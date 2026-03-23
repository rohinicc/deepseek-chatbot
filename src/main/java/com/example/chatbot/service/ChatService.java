package com.example.chatbot.service;

import com.example.chatbot.model.ChatMessage;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ChatService {

    private final ChatClient chatClient;

    private static final String SYSTEM_PROMPT = """
            You are an expert AI coding assistant. Always respond in English only.

            STRICT FORMATTING RULES:
            - Always write code with proper line breaks. Each statement must be on its own line.
            - Always use correct indentation (4 spaces for Python/Java, 2 for JS/HTML).
            - Always wrap ALL code in a markdown fenced code block with the language name.
            - NEVER put multiple statements on one line.
            - NEVER mix explanation text inside a code block.

            RESPONSE STRUCTURE — always follow this order:
            1. One short sentence explaining what the code does.
            2. The complete properly formatted code block.
            3. Maximum 3 bullet points explaining key parts.

            HISTORY AWARENESS:
            - Use previous messages for context on follow-up questions.
            - If the user says "fix this" or "improve this", refer to the previous code.
            """;

    public ChatService(ChatClient.Builder builder) {
        this.chatClient = builder
                .defaultSystem(SYSTEM_PROMPT)
                .build();
    }

    public String chat(List<ChatMessage> history, String userMessage) {
        List<Message> messages = new ArrayList<>();

        for (ChatMessage msg : history) {
            if ("user".equals(msg.getRole())) {
                messages.add(new UserMessage(msg.getContent()));
            } else if ("assistant".equals(msg.getRole())) {
                messages.add(new AssistantMessage(msg.getContent()));
            }
        }
        messages.add(new UserMessage(userMessage));

        return chatClient.prompt(new Prompt(messages))
                .call()
                .content();
    }
}