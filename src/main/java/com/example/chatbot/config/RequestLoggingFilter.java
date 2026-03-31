package com.example.chatbot.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RequestLoggingFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(RequestLoggingFilter.class);
    private static final String REQUEST_ID_HEADER = "X-Request-ID";
    private static final String REQUEST_ID_MDC_KEY = "requestId";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // Generate a UUID for the request if it doesn't already have one
        String requestId = request.getHeader(REQUEST_ID_HEADER);
        if (requestId == null || requestId.isBlank()) {
            requestId = UUID.randomUUID().toString();
        }

        // Add to MDC for logging
        MDC.put(REQUEST_ID_MDC_KEY, requestId);
        response.setHeader(REQUEST_ID_HEADER, requestId);

        long startTime = System.currentTimeMillis();

        try {
            logger.info("Incoming Request: {} {} [IP: {}]", request.getMethod(), request.getRequestURI(), request.getRemoteAddr());
            filterChain.doFilter(request, response);
        } finally {
            long latency = System.currentTimeMillis() - startTime;
            logger.info("Outgoing Response: {} {} (Status: {}) | Latency: {} ms",
                    request.getMethod(), request.getRequestURI(), response.getStatus(), latency);

            MDC.remove(REQUEST_ID_MDC_KEY);
        }
    }
}
