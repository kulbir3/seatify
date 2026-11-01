package com.seatify.service;

import com.seatify.entity.*;
import com.seatify.repository.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.Set;

@Service
@Transactional(isolation = Isolation.READ_COMMITTED)
public class BookingService {

    private final BookingRepo bookingRepo;
    private final EventRepo eventRepo;
    private final BookingDetailRepo bookingDetailRepo;
    private final SeatRepo seatRepo;
    private final UserRepo userRepo;

    public BookingService(
            BookingRepo bookingRepo,
            EventRepo eventRepo,
            BookingDetailRepo bookingDetailRepo,
            SeatRepo seatRepo,
            UserRepo userRepo) {

        this.bookingRepo = bookingRepo;
        this.eventRepo = eventRepo;
        this.bookingDetailRepo = bookingDetailRepo;
        this.seatRepo = seatRepo;
        this.userRepo = userRepo;
    }

    public Events getEventById(Long id) {
        return eventRepo.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Event not found!"));
    }

    public boolean isSeatAlreadyBooked(Long eventId, Long seatId) {
        return bookingDetailRepo
                .existsByEvent_IdAndSeat_IdAndBooking_Status(
                        eventId,
                        seatId,
                        Booking.BookingStatus.CONFIRMED
                );
    }

    public Booking getBookingForUser(Long bookingId, String userEmail) {

        User user = userRepo.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found!"));

        return bookingRepo.findByIdAndUser_Id(bookingId, user.getId())
                .orElseThrow(() ->
                        new RuntimeException("Booking not found!"));
    }

    public List<BookingDetail> getBookingDetails(Long bookingId) {
        return bookingDetailRepo.findByBooking_Id(bookingId);
    }

    public List<Booking> getUserBookings(String userEmail) {
        User user = userRepo.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found!"));

        return bookingRepo.findByUser_IdOrderByBookingDateDesc(user.getId());
    }

    public Set<Long> getBookedSeatIds(Long eventId) {
        return bookingDetailRepo
                .findByEvent_IdAndBooking_Status(
                        eventId,
                        Booking.BookingStatus.CONFIRMED
                )
                .stream()
                .map(detail -> detail.getSeat().getId())
                .collect(Collectors.toSet());
    }

    public Booking createBooking(
            Long eventId,
            List<Long> seatIds,
            String userEmail) {

        if (seatIds == null || seatIds.isEmpty()) {
            throw new RuntimeException("Please select at least one seat.");
        }

        Set<Long> uniqueSeatIds = new HashSet<>(seatIds);

        if (uniqueSeatIds.size() != seatIds.size()) {
            throw new RuntimeException("Duplicate seats were selected.");
        }

        Events event = getEventById(eventId);

        User user = userRepo.findByEmail(userEmail)
                .orElseThrow(() ->
                        new RuntimeException("User not found!"));

        Long venueId = event.getVenue().getId();

        List<Long> sortedSeatIds = seatIds.stream()
                .sorted()
                .toList();

        List<Seat> selectedSeats =
                seatRepo.findByIdsAndVenueWithLock(
                        sortedSeatIds,
                        venueId
                );

        if (selectedSeats.size() != uniqueSeatIds.size()) {
            throw new RuntimeException(
                    "One or more selected seats are invalid.");
        }

        BigDecimal total = BigDecimal.ZERO;

        for (Seat seat : selectedSeats) {

            if (isSeatAlreadyBooked(eventId, seat.getId())) {
                throw new RuntimeException(
                        "Seat " + seat.getSeatNumber()
                                + " is already booked.");
            }

            total = total.add(seat.getPrice());
        }


        Booking booking = new Booking();
        booking.setUser(user);
        booking.setEvent(event);
        booking.setTotalAmount(total);
        booking.setStatus(Booking.BookingStatus.CONFIRMED);

        booking = bookingRepo.save(booking);

        for (Seat seat : selectedSeats) {

            BookingDetail detail = new BookingDetail();
            detail.setBooking(booking);
            detail.setEvent(event);
            detail.setSeat(seat);
            detail.setPrice(seat.getPrice());

            bookingDetailRepo.save(detail);
        }

        return booking;
    }

    @Transactional
    public void cancelBooking(Long bookingId, String userEmail) {

        Booking booking = getBookingForUser(bookingId, userEmail);

        if (booking.getStatus() != Booking.BookingStatus.CONFIRMED) {
            throw new IllegalStateException(
                    "Only confirmed bookings can be cancelled."
            );
        }

        booking.setStatus(Booking.BookingStatus.CANCELLED);

        bookingRepo.save(booking);
    }

    @Transactional
    public void cancelSeat(
            Long bookingId,
            Long detailId,
            String userEmail) {

        Booking booking = getBookingForUser(bookingId, userEmail);

        if (booking.getStatus() != Booking.BookingStatus.CONFIRMED) {
            throw new IllegalStateException(
                    "This booking is not confirmed."
            );
        }

        BookingDetail detail = bookingDetailRepo
                .findByIdAndBooking_Id(detailId, bookingId)
                .orElseThrow(() ->
                        new RuntimeException("Seat not found in this booking.")
                );

        // Remove the seat from the booking.
        booking.setTotalAmount(
                booking.getTotalAmount().subtract(detail.getPrice())
        );

        bookingDetailRepo.delete(detail);
        bookingDetailRepo.flush();

        // If no seats remain, cancel the entire booking.
        List<BookingDetail> remainingDetails =
                bookingDetailRepo.findByBooking_Id(bookingId);

        if (remainingDetails.isEmpty()) {
            booking.setStatus(Booking.BookingStatus.CANCELLED);
        }

        bookingRepo.save(booking);
    }
}
