package com.seatify.controller;

import com.seatify.entity.Booking;
import com.seatify.service.BookingService;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping("/bookings/confirm")
    public String confirmBooking(
            @RequestParam Long eventId,
            @RequestParam List<Long> seatIds,
            Authentication authentication,
            RedirectAttributes redirectAttributes) {

        try {
            // Get the email of the logged-in user
            String userEmail = authentication.getName();

            // Create the booking
            Booking booking = bookingService.createBooking(
                    eventId,
                    seatIds,
                    userEmail
            );

            // Redirect after successful booking
            redirectAttributes.addFlashAttribute(
                    "successMessage",
                    "Booking confirmed successfully!"
            );

            return "redirect:/bookings/" + booking.getId() + "/confirmation";

        } catch (RuntimeException e) {

            redirectAttributes.addFlashAttribute(
                    "errorMessage",
                    e.getMessage()
            );

            return "redirect:/events/" + eventId + "/seats";
        }
    }

    @GetMapping("/bookings/{bookingId}/confirmation")
    public String showConfirmation(
            @PathVariable Long bookingId,
            Authentication authentication,
            Model model) {

        Booking booking = bookingService.getBookingForUser(
                bookingId,
                authentication.getName()
        );

        model.addAttribute("booking", booking);
        model.addAttribute(
                "bookingDetails",
                bookingService.getBookingDetails(bookingId)
        );

        return "booking-confirmation";
    }

    @PostMapping("/bookings/{bookingId}/cancel")
    public String cancelBooking(
            @PathVariable Long bookingId,
            Authentication authentication,
            RedirectAttributes redirectAttributes) {

        try {
            bookingService.cancelBooking(
                    bookingId,
                    authentication.getName()
            );

            redirectAttributes.addFlashAttribute(
                    "successMessage",
                    "Booking cancelled successfully."
            );

        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute(
                    "errorMessage",
                    e.getMessage()
            );
        }

        return "redirect:/my-bookings";
    }

    @PostMapping("/bookings/{bookingId}/cancel-seat/{detailId}")
    public String cancelSeat(
            @PathVariable Long bookingId,
            @PathVariable Long detailId,
            Authentication authentication,
            RedirectAttributes redirectAttributes) {

        try {
            bookingService.cancelSeat(
                    bookingId,
                    detailId,
                    authentication.getName()
            );

            redirectAttributes.addFlashAttribute(
                    "successMessage",
                    "Seat cancelled successfully."
            );

        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute(
                    "errorMessage",
                    e.getMessage()
            );
        }

        return "redirect:/my-bookings";
    }
}
