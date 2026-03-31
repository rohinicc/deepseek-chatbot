package com.example.chatbot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class DeepseekChatbotApplication {

	public static void main(String[] args) {
		SpringApplication.run(DeepseekChatbotApplication.class, args);
	}

}
