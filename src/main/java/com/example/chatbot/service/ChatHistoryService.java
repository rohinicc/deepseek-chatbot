package com.example.chatbot.service;

import com.example.chatbot.model.ChatMessage;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Stores each user's chat history in Redis.
 *
 * Key   : chat:history:{username}
 * Value : JSON array of ChatMessage objects
 * TTL   : 24 hours by default (resets on every new message)
 *
 * Benefits over HttpSession:
 *   - Survives server restarts
 *   - Works across multiple instances
 *   - History isolated per user
 */
@Service
public class ChatHistoryService {

    private static final String PREFIX = "chat:history:";

    private final RedisTemplate<String, Object> redis;
    private final ObjectMapper                  mapper;

    @Value("${app.chat.ttl-hours:24}")
    private long ttlHours;

    public ChatHistoryService(RedisTemplate<String, Object> redis,
                              ObjectMapper mapper) {
        this.redis  = redis;
        this.mapper = mapper;
    }

    /** Returns full history for the user, or an empty list if none. */
    public List<ChatMessage> getHistory(String username) {
        Object raw = redis.opsForValue().get(key(username));
        if (raw == null) return new ArrayList<>();
        try {
            return mapper.convertValue(raw,
                    new TypeReference<List<ChatMessage>>() {});
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    /** Appends one message and resets the TTL. */
    public void addMessage(String username, ChatMessage message) {
        List<ChatMessage> history = getHistory(username);
        history.add(message);
        redis.opsForValue().set(key(username), history, ttlHours, TimeUnit.HOURS);
    }

    /** Deletes the entire chat history for the user. */
    public void clearHistory(String username) {
        redis.delete(key(username));
    }

    private String key(String username) {
        return PREFIX + username;
    }
}
