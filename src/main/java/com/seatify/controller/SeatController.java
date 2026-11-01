package com.seatify.controller;

import com.seatify.entity.Events;
import com.seatify.service.BookingService;
import com.seatify.service.SeatService;
import com.seatify.service.EventService;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.stereotype.Controller;

@Controller
public class SeatController {
    private final SeatService seatService;
    private final BookingService bookingService;
    private final EventService eventService;

    public SeatController(SeatService seatService, BookingService bookingService, EventService eventService) {
        this.seatService = seatService;
        this.bookingService = bookingService;
        this.eventService = eventService;
    }

    @GetMapping("/events/{eventId}/seats")
    public String showSeats(
            @PathVariable Long eventId,
            Model model) {

        Events event = eventService.getEventById(eventId);

        if (event == null) {
            return "redirect:/";
        }

        Long venueId = event.getVenue().getId();

        var seats = seatService.getSeatsByVenueId(venueId);

        model.addAttribute("event", event);
        model.addAttribute("seats", seats);
        model.addAttribute(
                "bookedSeatIds",
                bookingService.getBookedSeatIds(eventId)
        );

        return "seat-Selection";
    }
}
