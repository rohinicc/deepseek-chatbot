package com.example.chatbot.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "users",
       uniqueConstraints = @UniqueConstraint(columnNames = "username"))
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String firstName;

    @Column(nullable = false, length = 50)
    private String lastName;

    @Column(nullable = false, unique = true, length = 50)
    private String username;

    @Column(nullable = false)
    private String password;           // BCrypt hashed — never plain text

    @Column(nullable = false)
    private boolean enabled = true;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    // ── Constructors ──────────────────────────────────────────────────────
    public User() {}

    public User(String firstName, String lastName,
                String username, String password) {
        this.firstName = firstName;
        this.lastName  = lastName;
        this.username  = username;
        this.password  = password;
    }

    // ── Getters & Setters ─────────────────────────────────────────────────
    public Long          getId()          { return id; }
    public String        getFirstName()   { return firstName; }
    public String        getLastName()    { return lastName; }
    public String        getUsername()    { return username; }
    public String        getPassword()    { return password; }
    public boolean       isEnabled()      { return enabled; }
    public LocalDateTime getCreatedAt()   { return createdAt; }

    public void setFirstName(String v)   { this.firstName = v; }
    public void setLastName(String v)    { this.lastName  = v; }
    public void setUsername(String v)    { this.username  = v; }
    public void setPassword(String v)    { this.password  = v; }
    public void setEnabled(boolean v)    { this.enabled   = v; }
}