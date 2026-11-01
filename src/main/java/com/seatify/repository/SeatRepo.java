package com.seatify.repository;

import com.seatify.entity.Seat;
import jakarta.persistence.LockModeType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SeatRepo extends JpaRepository<Seat, Long> {

    List<Seat> findByVenue_Id(Long venueId);

    List<Seat> findByIdInAndVenue_Id(List<Long> ids, Long venueId);

    boolean existsByVenue_IdAndSeatNumber(Long venueId, String seatNumber);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("""
        SELECT s FROM Seat s
        WHERE s.id IN :ids
        AND s.venue.id = :venueId
        ORDER BY s.id
        """)
    List<Seat> findByIdsAndVenueWithLock(
            @Param("ids") List<Long> ids,
            @Param("venueId") Long venueId
    );
}