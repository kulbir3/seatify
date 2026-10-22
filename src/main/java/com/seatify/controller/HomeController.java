package com.seatify.controller;

import com.seatify.entity.Category;
import com.seatify.service.EventService;
import com.seatify.repository.CategoryRepo;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Controller
public class HomeController {

    private final EventService eventService;
    private final CategoryRepo categoryRepo;

    public HomeController(
            EventService eventService,
            CategoryRepo categoryRepo) {
        this.eventService = eventService;
        this.categoryRepo = categoryRepo;
    }

    @GetMapping("/")
    public String home(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate date,
            Model model) {

        model.addAttribute(
                "events",
                eventService.filterEvents(name, categoryId, date)
        );

        model.addAttribute("categories", categoryRepo.findAll());
        model.addAttribute("searchName", name == null ? "" : name);
        model.addAttribute("categoryId", categoryId);
        model.addAttribute("selectedDate", date == null ? "" : date);

        return "home";
    }
}