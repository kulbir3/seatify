package com.seatify.repository;

import com.seatify.entity.Booking;
import com.seatify.entity.Booking.BookingStatus;
import com.seatify.entity.BookingDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingDetailRepo
        extends JpaRepository<BookingDetail, Long> {

    List<BookingDetail> findByBooking_Id(Long bookingId);

    List<BookingDetail> findByEvent_Id(Long eventId);

    boolean existsByEvent_IdAndSeat_IdAndBooking_Status(
            Long eventId,
            Long seatId,
            BookingStatus status
    );

    List<BookingDetail> findByEvent_IdAndBooking_Status(
            Long eventId,
            BookingStatus status
    );
}