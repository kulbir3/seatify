package com.seatify.controller;

import com.seatify.entity.Events;
import com.seatify.repository.CategoryRepo;
import com.seatify.repository.VenueRepo;
import com.seatify.service.AdminEventService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Controller
@RequestMapping("/admin/events")
public class AdminEventController {

    private final AdminEventService eventService;
    private final CategoryRepo categoryRepo;
    private final VenueRepo venueRepo;

    public AdminEventController(
            AdminEventService eventService,
            CategoryRepo categoryRepo,
            VenueRepo venueRepo) {

        this.eventService = eventService;
        this.categoryRepo = categoryRepo;
        this.venueRepo = venueRepo;
    }

    @GetMapping
    public String showEvents(Model model) {
        model.addAttribute("events", eventService.getAllEvents());
        return "admin/events";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        loadFormData(model);
        return "admin/add-event";
    }

    @PostMapping("/add")
    public String addEvent(
            @RequestParam String name,
            @RequestParam(required = false) String description,
            @RequestParam Long categoryId,
            @RequestParam Long venueId,
            @RequestParam LocalDate eventDate,
            @RequestParam LocalTime startTime,
            @RequestParam(required = false) LocalTime endTime,
            @RequestParam BigDecimal price,
            @RequestParam(required = false) String imageUrl,
            RedirectAttributes redirectAttributes) {

        try {
            eventService.addEvent(name, description, categoryId, venueId,
                    eventDate, startTime, endTime, price, imageUrl);

            redirectAttributes.addFlashAttribute(
                    "successMessage", "Event added successfully!");

        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute(
                    "errorMessage", e.getMessage());
            return "redirect:/admin/events/add";
        }

        return "redirect:/admin/events";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("event", eventService.getEventById(id));
        loadFormData(model);
        return "admin/edit-event";
    }

    @PostMapping("/edit/{id}")
    public String updateEvent(
            @PathVariable Long id,
            @RequestParam String name,
            @RequestParam(required = false) String description,
            @RequestParam Long categoryId,
            @RequestParam Long venueId,
            @RequestParam LocalDate eventDate,
            @RequestParam LocalTime startTime,
            @RequestParam(required = false) LocalTime endTime,
            @RequestParam BigDecimal price,
            @RequestParam(required = false) String imageUrl,
            @RequestParam Events.EventStatus status,
            RedirectAttributes redirectAttributes) {

        try {
            eventService.updateEvent(id, name, description,
                    categoryId, venueId, eventDate, startTime,
                    endTime, price, imageUrl, status);

            redirectAttributes.addFlashAttribute(
                    "successMessage", "Event updated successfully!");

        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute(
                    "errorMessage", e.getMessage());
            return "redirect:/admin/events/edit/" + id;
        }

        return "redirect:/admin/events";
    }

    @PostMapping("/delete/{id}")
    public String deleteEvent(
            @PathVariable Long id,
            RedirectAttributes redirectAttributes) {

        try {
            eventService.deleteEvent(id);
            redirectAttributes.addFlashAttribute(
                    "successMessage", "Event deleted successfully!");

        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute(
                    "errorMessage", e.getMessage());
        }

        return "redirect:/admin/events";
    }

    private void loadFormData(Model model) {
        model.addAttribute("categories", categoryRepo.findAll());
        model.addAttribute("venues", venueRepo.findAll());
        model.addAttribute("statuses", Events.EventStatus.values());
    }
}