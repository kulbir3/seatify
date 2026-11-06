package com.seatify.controller;

import com.seatify.service.AdminService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/admin")
    public String showDashboard(Model model) {

        model.addAttribute("totalUsers", adminService.getTotalUsers());
        model.addAttribute("totalEvents", adminService.getTotalEvents());
        model.addAttribute("totalBookings", adminService.getTotalBookings());
        model.addAttribute("confirmedBookings", adminService.getConfirmedBookings());
        model.addAttribute("cancelledBookings", adminService.getCancelledBookings());

        return "admin/dashboard";
    }
}