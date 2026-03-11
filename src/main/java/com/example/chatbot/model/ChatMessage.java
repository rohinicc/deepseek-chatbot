
package com.example.chatbot.model;

public class ChatMessage {

    private String role;
    private String content;
    private String timestamp;

    public ChatMessage() {}

    public ChatMessage(String role, String content, String timestamp) {
        this.role      = role;
        this.content   = content;
        this.timestamp = timestamp;
    }

    public String getRole()                { return role; }
    public void   setRole(String role)     { this.role = role; }

    public String getContent()             { return content; }
    public void   setContent(String c)     { this.content = c; }

    public String getTimestamp()           { return timestamp; }
    public void   setTimestamp(String t)   { this.timestamp = t; }
}