package com.seatify.service;

import com.seatify.entity.Events;
import com.seatify.repository.EventRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class EventService {

    private final EventRepo eventRepo;

    public EventService(EventRepo eventRepo) {
        this.eventRepo = eventRepo;
    }

    public List<Events> getAllEvents() {
        return eventRepo.findAll();
    }

    public Events getEventById(Long id) {
        return eventRepo.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("Event not found: " + id));
    }

    public List<Events> searchEvents(String name) {
        if (name == null || name.isBlank()) {
            return getAllEvents();
        }

        return eventRepo.findByNameContainingIgnoreCase(name.trim());
    }

    public List<Events> getEventsByCategory(Long categoryId) {
        return eventRepo.findByCategory_Id(categoryId);
    }

    public List<Events> getUpcomingEvents() {
        return eventRepo.findByEventDateGreaterThanEqual(LocalDate.now());
    }
}