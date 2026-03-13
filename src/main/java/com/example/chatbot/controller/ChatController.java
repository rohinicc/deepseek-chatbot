package com.example.chatbot.controller;

import com.example.chatbot.model.ChatMessage;
import com.example.chatbot.service.ChatService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
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

    private final ChatService chatService;
    private static final String SESSION_KEY = "chatHistory";
    private static final DateTimeFormatter TIME_FMT =
            DateTimeFormatter.ofPattern("HH:mm");

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
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
    public String index(Model model, HttpSession session) {
        model.addAttribute("messages", getHistory(session));
        return "index";
    }

    
    @PostMapping("/chat")
    @ResponseBody
    public ResponseEntity<Map<String, String>> chat(
            @RequestBody Map<String, String> body,
            HttpSession session) {

        String userMessage = body.get("message");
        if (userMessage == null || userMessage.isBlank()) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "Message cannot be empty."));
        }

        List<ChatMessage> history = getHistory(session);
        String now = LocalTime.now().format(TIME_FMT);

        history.add(new ChatMessage("user", userMessage, now));

        String reply = chatService.chat(
                history.subList(0, history.size() - 1),
                userMessage
        );

        history.add(new ChatMessage("assistant", reply, now));
        session.setAttribute(SESSION_KEY, history);

        return ResponseEntity.ok(Map.of("response", reply, "time", now));
    }

    
    @PostMapping("/clear")
    @ResponseBody
    public ResponseEntity<Map<String, String>> clear(HttpSession session) {
        session.removeAttribute(SESSION_KEY);
        return ResponseEntity.ok(Map.of("status", "cleared"));
    }

    
    @SuppressWarnings("unchecked")
    private List<ChatMessage> getHistory(HttpSession session) {
        Object stored = session.getAttribute(SESSION_KEY);
        if (stored instanceof List<?> list) {
            return (List<ChatMessage>) list;
        }
        List<ChatMessage> fresh = new ArrayList<>();
        session.setAttribute(SESSION_KEY, fresh);
        return fresh;
    }
}
