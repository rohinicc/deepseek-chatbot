package com.example.chatbot.controller;

import com.example.chatbot.service.AuthService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class Authcontroller {

    private final AuthService authService;

    public Authcontroller(AuthService authService) {
        this.authService = authService;
    }

    // ── GET /login ────────────────────────────────────────────────────────────
    // FIX #3: Spring Security redirects to /login?error on bad credentials,
    // but also calls this controller method. We must NOT re-add an "error"
    // model attribute here when param.error is present — login.html handles
    // that via th:if="${param.error != null}" directly. The controller only
    // handles ?registered and ?logout which Spring Security does NOT handle.
    @GetMapping("/login")
    public String loginPage(
            @RequestParam(name = "error",      required = false) String error,
            @RequestParam(name = "logout",     required = false) String logout,
            @RequestParam(name = "registered", required = false) String registered,
            Model model) {

        // Do NOT add model error for "error" param — login.html reads param.error directly.
        // Adding it here creates a duplicate banner or conflicts with Thymeleaf param check.
        if (logout     != null) model.addAttribute("success", "You have been signed out.");
        if (registered != null) model.addAttribute("success", "Account created — please sign in.");
        return "login";
    }

    // ── GET /signup ───────────────────────────────────────────────────────────
    @GetMapping("/signup")
    public String signupPage() {
        return "signup";
    }

    // ── POST /signup ──────────────────────────────────────────────────────────
    @PostMapping("/signup")
    public String signupSubmit(
            // FIX #4: defaultValue="" on all params prevents
            // MissingServletRequestParameterException if any field is missing
            @RequestParam(name = "firstName",       required = false, defaultValue = "") String firstName,
            @RequestParam(name = "lastName",        required = false, defaultValue = "") String lastName,
            @RequestParam(name = "username",        required = false, defaultValue = "") String username,
            @RequestParam(name = "password",        required = false, defaultValue = "") String password,
            @RequestParam(name = "confirmPassword", required = false, defaultValue = "") String confirmPassword,
            // FIX #5: terms checkbox — browsers do NOT send unchecked checkboxes at all.
            // defaultValue="" means terms="" when unchecked, so isEmpty() catches it correctly.
            @RequestParam(name = "terms",           required = false, defaultValue = "") String terms,
            Model model) {

        // Re-populate form fields on every validation failure
        model.addAttribute("firstName", firstName);
        model.addAttribute("lastName",  lastName);
        model.addAttribute("username",  username);

        // Validation order: terms → names → username → password → match
        if (terms.isEmpty()) {
            model.addAttribute("error", "You must agree to the Terms of Service.");
            return "signup";
        }

        if (firstName.isBlank() || lastName.isBlank()) {
            model.addAttribute("error", "First and last name are required.");
            return "signup";
        }

        if (!username.matches("^[a-zA-Z0-9_]{3,50}$")) {
            model.addAttribute("error", "Username must be 3–50 characters (letters, numbers, underscores).");
            return "signup";
        }

        if (password.length() < 8) {
            model.addAttribute("error", "Password must be at least 8 characters.");
            return "signup";
        }

        if (!password.equals(confirmPassword)) {
            model.addAttribute("error", "Passwords do not match.");
            return "signup";
        }

        try {
            authService.register(firstName, lastName, username, password);
            // PRG pattern — redirect prevents duplicate submission on browser refresh
            return "redirect:/login?registered";
        } catch (Exception ex) {
            model.addAttribute("error", ex.getMessage());
            return "signup";
        }
    }
}