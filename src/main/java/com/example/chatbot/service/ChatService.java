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
            You are DeepSeek Coder, an expert AI coding assistant.
            - Provide clear, well-commented code examples.
            - Use proper markdown fenced code blocks with the language identifier.
            - Keep explanations concise and precise.
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
