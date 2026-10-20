package com.seatify.repository;

import com.seatify.entity.Events;
import com.seatify.entity.Events.EventStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface EventRepo extends JpaRepository<Events, Long> {

    List<Events> findByNameContainingIgnoreCase(String name);

    List<Events> findByCategory_Id(Long categoryId);

    List<Events> findByEventDateGreaterThanEqual(LocalDate date);

    List<Events> findByStatus(EventStatus status);

    List<Events> findByNameContainingIgnoreCaseAndCategory_Id(
            String name, Long categoryId);

    List<Events> findByEventDateGreaterThanEqualAndStatus(
            LocalDate date, EventStatus status);
}