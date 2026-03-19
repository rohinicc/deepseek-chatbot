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
import java.util.List;
import java.util.Map;

/**
 * Chat controller.
 * History is stored in Redis (not HttpSession) so it survives server restarts
 * and is isolated per logged-in user.
 */
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

    /** Loads chat page — pulls history from Redis for this user */
    @GetMapping("/chat-page")
    public String index(Model model,
                        @AuthenticationPrincipal UserDetails principal) {
        String username = principal.getUsername();
        model.addAttribute("messages", historyService.getHistory(username));
        model.addAttribute("username", username);
        return "index";
    }

    /** Receives a message, calls AI, stores both turns in Redis */
    @PostMapping("/chat")
    @ResponseBody
    public ResponseEntity<Map<String, String>> chat(
            @RequestBody Map<String, String> body,
            @AuthenticationPrincipal UserDetails principal) {

        String userMessage = body.get("message");
        if (userMessage == null || userMessage.isBlank()) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "Message cannot be empty."));
        }

        String username = principal.getUsername();
        String now      = LocalTime.now().format(TIME_FMT);

        // Get current history to send as context, then persist user message
        List<ChatMessage> history = historyService.getHistory(username);
        historyService.addMessage(username, new ChatMessage("user", userMessage, now));

        // Call AI with full history as context
        String reply = chatService.chat(history, userMessage);

        // Persist AI reply
        historyService.addMessage(username, new ChatMessage("assistant", reply, now));

        return ResponseEntity.ok(Map.of("response", reply, "time", now));
    }

    /** Clears this user's chat history from Redis */
    @PostMapping("/clear")
    @ResponseBody
    public ResponseEntity<Map<String, String>> clear(
            @AuthenticationPrincipal UserDetails principal) {
        historyService.clearHistory(principal.getUsername());
        return ResponseEntity.ok(Map.of("status", "cleared"));
    }
}