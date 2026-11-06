package com.seatify.service;

import com.seatify.entity.Booking.BookingStatus;
import com.seatify.repository.BookingRepo;
import com.seatify.repository.EventRepo;
import com.seatify.repository.UserRepo;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    private final UserRepo userRepo;
    private final EventRepo eventRepo;
    private final BookingRepo bookingRepo;

    public AdminService(
            UserRepo userRepo,
            EventRepo eventRepo,
            BookingRepo bookingRepo) {

        this.userRepo = userRepo;
        this.eventRepo = eventRepo;
        this.bookingRepo = bookingRepo;
    }

    public long getTotalUsers() {
        return userRepo.count();
    }

    public long getTotalEvents() {
        return eventRepo.count();
    }

    public long getTotalBookings() {
        return bookingRepo.count();
    }

    public long getConfirmedBookings() {
        return bookingRepo.countByStatus(BookingStatus.CONFIRMED);
    }

    public long getCancelledBookings() {
        return bookingRepo.countByStatus(BookingStatus.CANCELLED);
    }
}