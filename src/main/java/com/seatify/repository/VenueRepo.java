package com.seatify.repository;

import com.seatify.entity.Venue;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VenueRepo extends JpaRepository<Venue, Long> {

    boolean existsByNameIgnoreCase(String name);
}