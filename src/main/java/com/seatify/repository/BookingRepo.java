package com.seatify.repository;

import com.seatify.entity.Booking;
import com.seatify.entity.Booking.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepo extends JpaRepository<Booking, Long> {

    List<Booking> findByUser_IdOrderByBookingDateDesc(Long userId);

    List<Booking> findByStatus(BookingStatus status);

    List<Booking> findByEvent_Id(Long eventId);

    List<Booking> findByEvent_IdAndStatus(
            Long eventId,
            BookingStatus status
    );
}