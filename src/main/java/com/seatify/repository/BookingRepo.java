package com.seatify.repository;

import com.seatify.entity.Booking;
import com.seatify.entity.Booking.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.*;

public interface BookingRepo extends JpaRepository<Booking, Long> {

    List<Booking> findByUser_IdOrderByBookingDateDesc(Long userId);

    List<Booking> findByStatus(BookingStatus status);

    List<Booking> findByEvent_Id(Long eventId);

    Optional<Booking> findByIdAndUser_Id(Long bookingId, Long userId);

    List<Booking> findByEvent_IdAndStatus(
            Long eventId,
            BookingStatus status
    );

    long countByStatus(BookingStatus status);

    boolean existsByEvent_Id(Long eventId);
}