package com.seatify.service;

import com.seatify.entity.Events;
import com.seatify.repository.BookingRepo;
import com.seatify.repository.CategoryRepo;
import com.seatify.repository.EventRepo;
import com.seatify.repository.VenueRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class AdminEventService {

    private final EventRepo eventRepo;
    private final CategoryRepo categoryRepo;
    private final VenueRepo venueRepo;
    private final BookingRepo bookingRepo;

    public AdminEventService(
            EventRepo eventRepo,
            CategoryRepo categoryRepo,
            VenueRepo venueRepo,
            BookingRepo bookingRepo) {

        this.eventRepo = eventRepo;
        this.categoryRepo = categoryRepo;
        this.venueRepo = venueRepo;
        this.bookingRepo = bookingRepo;
    }

    public List<Events> getAllEvents() {
        return eventRepo.findAll();
    }

    public Events getEventById(Long id) {
        return eventRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found."));
    }

    public void addEvent(
            String name,
            String description,
            Long categoryId,
            Long venueId,
            LocalDate eventDate,
            LocalTime startTime,
            LocalTime endTime,
            BigDecimal price,
            String imageUrl) {

        Events event = new Events();

        setEventDetails(event, name, description, categoryId,
                venueId, eventDate, startTime, endTime, price, imageUrl);

        event.setStatus(Events.EventStatus.UPCOMING);

        eventRepo.save(event);
    }

    public void updateEvent(
            Long id,
            String name,
            String description,
            Long categoryId,
            Long venueId,
            LocalDate eventDate,
            LocalTime startTime,
            LocalTime endTime,
            BigDecimal price,
            String imageUrl,
            Events.EventStatus status) {

        Events event = getEventById(id);

        setEventDetails(event, name, description, categoryId,
                venueId, eventDate, startTime, endTime, price, imageUrl);

        event.setStatus(status);

        eventRepo.save(event);
    }

    private void setEventDetails(
            Events event,
            String name,
            String description,
            Long categoryId,
            Long venueId,
            LocalDate eventDate,
            LocalTime startTime,
            LocalTime endTime,
            BigDecimal price,
            String imageUrl) {

        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Event name is required.");
        }

        if (price == null || price.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Price must be greater than zero.");
        }

        if (eventDate == null || startTime == null) {
            throw new IllegalArgumentException("Event date and start time are required.");
        }

        if (endTime != null && !endTime.isAfter(startTime)) {
            throw new IllegalArgumentException("End time must be after start time.");
        }

        event.setName(name.trim());
        event.setDescription(description);
        event.setCategory(categoryRepo.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found.")));
        event.setVenue(venueRepo.findById(venueId)
                .orElseThrow(() -> new RuntimeException("Venue not found.")));
        event.setEventDate(eventDate);
        event.setStartTime(startTime);
        event.setEndTime(endTime);
        event.setPrice(price);
        event.setImageUrl(imageUrl);
    }

    @Transactional
    public void deleteEvent(Long id) {
        Events event = getEventById(id);

        if (bookingRepo.existsByEvent_Id(id)) {
            throw new IllegalStateException(
                    "This event has bookings and cannot be deleted."
            );
        }

        eventRepo.delete(event);
    }

    public void updateStatus(Long id, Events.EventStatus status) {
        Events event = getEventById(id);
        event.setStatus(status);
        eventRepo.save(event);
    }
}