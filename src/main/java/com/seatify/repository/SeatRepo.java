package com.seatify.repository;

import com.seatify.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SeatRepo extends JpaRepository<Seat, Long> {

    List<Seat> findByVenue_Id(Long venueId);

    boolean existsByVenue_IdAndSeatNumber(Long venueId, String seatNumber);
}