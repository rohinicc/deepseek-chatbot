package com.example.chatbot.controller;

import com.example.chatbot.service.AuthService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
public class Authcontroller {

    private final AuthService authService;

    public Authcontroller(AuthService authService) {
        this.authService = authService;
    }

    // ── GET /login ────────────────────────────────────────────
    @GetMapping("/login")
    public String loginPage(
            @RequestParam(required = false) String error,
            @RequestParam(required = false) String logout,
            @RequestParam(required = false) String registered,
            Model model) {

        if (error      != null) model.addAttribute("error",   "Invalid username or password.");
        if (logout     != null) model.addAttribute("success", "You have been signed out.");
        if (registered != null) model.addAttribute("success", "Account created — please sign in.");
        return "login";
    }

    // ── GET /signup ───────────────────────────────────────────
    @GetMapping("/signup")
    public String signupPage() {
        return "signup";
    }

    // ── POST /signup ──────────────────────────────────────────
    @PostMapping("/signup")
    public String signupSubmit(
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam String confirmPassword,
            Model model) {

        if (!username.matches("^[a-zA-Z0-9_]{3,50}$")) {
            model.addAttribute("error",
                    "Username must be 3–50 characters (letters, numbers, underscores).");
            model.addAllAttributes(Map.of(
                    "firstName", firstName,
                    "lastName",  lastName,
                    "username",  username));
            return "signup";
        }

        if (password.length() < 8) {
            model.addAttribute("error", "Password must be at least 8 characters.");
            model.addAllAttributes(Map.of(
                    "firstName", firstName,
                    "lastName",  lastName,
                    "username",  username));
            return "signup";
        }

        if (!password.equals(confirmPassword)) {
            model.addAttribute("error", "Passwords do not match.");
            model.addAllAttributes(Map.of(
                    "firstName", firstName,
                    "lastName",  lastName,
                    "username",  username));
            return "signup";
        }

        try {
            authService.register(firstName, lastName, username, password);
            return "redirect:/login?registered";
        } catch (IllegalArgumentException ex) {
            model.addAttribute("error", ex.getMessage());
            model.addAllAttributes(Map.of(
                    "firstName", firstName,
                    "lastName",  lastName,
                    "username",  username));
            return "signup";
        }
    }
}