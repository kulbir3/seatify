package com.seatify.controller;

import com.seatify.entity.Booking;
import com.seatify.entity.BookingDetail;
import com.seatify.service.BookingService;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.*;

@Controller
public class MyBookingsController {

    private final BookingService bookingService;

    public MyBookingsController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping("/my-bookings")
    public String showMyBookings(
            Authentication authentication,
            Model model) {

        List<Booking> bookings =
                bookingService.getUserBookings(authentication.getName());

        model.addAttribute("bookings", bookings);

        Map<Long, List<BookingDetail>> bookingDetailsMap = new HashMap<>();

        for (Booking booking : bookings) {
            bookingDetailsMap.put(
                    booking.getId(),
                    bookingService.getBookingDetails(booking.getId())
            );
        }

        model.addAttribute("bookingDetailsMap", bookingDetailsMap);

        return "my-bookings";
    }
}