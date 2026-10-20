package com.seatify.controller;

import com.seatify.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String showRegisterPage(){
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(
            @RequestParam String name,
            @RequestParam String email,
            @RequestParam String password,
            Model model){
        try{
            if(name.isBlank() || email.isBlank() || password.isBlank()){
                model.addAttribute("error",
                        "All field are required.");
                return "register";
            }
            if(password.length()<8){
                model.addAttribute("error","Password must contain at least 8 character.");
                return "register";
            }

            userService.registerUser(
                    name.trim(),
                    email.trim().toLowerCase(),
                    password
            );
            return "redirect:/login?registered";
        }catch (IllegalArgumentException e){
            model.addAttribute("error",e.getMessage());
            return "register";
        }

    }
    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }
}
