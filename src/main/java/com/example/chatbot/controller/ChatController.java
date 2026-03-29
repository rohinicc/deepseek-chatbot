package com.example.chatbot.controller;

import com.example.chatbot.model.ChatMessage;
import com.example.chatbot.service.ChatHistoryService;
import com.example.chatbot.service.ChatService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class ChatController {

    private final ChatService        chatService;
    private final ChatHistoryService historyService;

    private static final DateTimeFormatter TIME_FMT =
            DateTimeFormatter.ofPattern("HH:mm");

    public ChatController(ChatService chatService,
                          ChatHistoryService historyService) {
        this.chatService    = chatService;
        this.historyService = historyService;
    }

    @GetMapping("/")
    public String root() {
        return "redirect:/welcome";
    }

    @GetMapping("/welcome")
    public String welcome() {
        return "welcome";
    }

    @GetMapping("/chat-page")
    public String index(Model model,
                        @AuthenticationPrincipal UserDetails principal) {

        // ── FIX: guard against null principal (unauthenticated access) ────────
        // If somehow this endpoint is hit without a valid session,
        // redirect to login instead of crashing with NullPointerException.
        if (principal == null) {
            return "redirect:/login";
        }

        String username = principal.getUsername();

        // ── FIX: always guarantee messages is a non-null List ─────────────────
        // historyService.getHistory() can return null if Redis is empty or
        // if there is a deserialization issue. A null list causes
        // ${#lists.isEmpty(messages)} in Thymeleaf to throw an exception,
        // which produces a blank white page with no error shown.
        List<ChatMessage> messages = historyService.getHistory(username);
        if (messages == null) {
            messages = new ArrayList<>();
        }

        model.addAttribute("messages", messages);
        model.addAttribute("username", username);
        return "index";
    }

    @PostMapping("/chat-page")
    @ResponseBody
    public ResponseEntity<Map<String, String>> chat(
            @RequestBody Map<String, String> body,
            @AuthenticationPrincipal UserDetails principal) {

        if (principal == null) {
            return ResponseEntity.status(401)
                    .body(Map.of("error", "Not authenticated"));
        }

        String userMessage = body.get("message");
        if (userMessage == null || userMessage.isBlank()) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "Message cannot be empty."));
        }

        String username = principal.getUsername();
        String now      = LocalTime.now().format(TIME_FMT);

        List<ChatMessage> history = historyService.getHistory(username);
        if (history == null) history = new ArrayList<>();

        historyService.addMessage(username, new ChatMessage("user", userMessage, now));

        String reply = chatService.chat(history, userMessage);
        historyService.addMessage(username, new ChatMessage("assistant", reply, now));

        return ResponseEntity.ok(Map.of("response", reply, "time", now));
    }

    @PostMapping("/clear")
    @ResponseBody
    public ResponseEntity<Map<String, String>> clear(
            @AuthenticationPrincipal UserDetails principal) {

        if (principal == null) {
            return ResponseEntity.status(401)
                    .body(Map.of("error", "Not authenticated"));
        }

        historyService.clearHistory(principal.getUsername());
        return ResponseEntity.ok(Map.of("status", "cleared"));
    }
}