package com.example.chatbot.service;

import com.example.chatbot.model.User;
import com.example.chatbot.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

    private final UserRepository  userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository  = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Saves a new user to MySQL.
     * Password is BCrypt-hashed before saving — never stored as plain text.
     * Account is active immediately — no email verification needed.
     */
    @Transactional
    public User register(String firstName, String lastName,
                         String username,  String rawPassword) {

        if (userRepository.existsByUsername(username)) {
            throw new IllegalArgumentException(
                    "Username \"" + username + "\" is already taken.");
        }

        User user = new User(
                firstName,
                lastName,
                username,
                passwordEncoder.encode(rawPassword)   // hash here
        );
        return userRepository.save(user);
    }
}