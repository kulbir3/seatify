package com.seatify.controller;

import  com.seatify.entity.Events;
import com.seatify.service.EventService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class EventController {
    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }
    @GetMapping("/events/{id}")
    public String eventDetails(
        @PathVariable Long id,
        Model model) {
        Events event = eventService.getEventById(id);

        if(event == null){
            return "redirect:/?Not found";
        }
        model.addAttribute("event", event);
        return "event-details";
    }
}
