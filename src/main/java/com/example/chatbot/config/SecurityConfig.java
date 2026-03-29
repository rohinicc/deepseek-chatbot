package com.example.chatbot.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.*;
import java.util.function.Supplier;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * Canonical Spring Security 6 pattern for apps that use BOTH:
     *   - Thymeleaf server-rendered forms  (need ${_csrf.token} in model)
     *   - JavaScript fetch() / XHR         (need X-XSRF-TOKEN header support)
     *
     * XorCsrfTokenRequestAttributeHandler  → writes token into request attributes
     *   so Thymeleaf ${_csrf.token} and ${_csrf.parameterName} resolve correctly.
     *
     * CsrfTokenRequestAttributeHandler     → resolves token value from either
     *   the form parameter (_csrf) OR the X-XSRF-TOKEN header, enabling JSON POSTs.
     */
    static final class SpaCsrfTokenRequestHandler implements CsrfTokenRequestHandler {

        private final CsrfTokenRequestHandler plain =
                new CsrfTokenRequestAttributeHandler();

        private final CsrfTokenRequestHandler xor =
                new XorCsrfTokenRequestAttributeHandler();

        @Override
        public void handle(HttpServletRequest request,
                           HttpServletResponse response,
                           Supplier<CsrfToken> csrfToken) {
            /*
             * Always use XorCsrfTokenRequestAttributeHandler to provide BREACH
             * protection of the CsrfToken when it is rendered in the response body.
             * This populates the _csrf request attribute so Thymeleaf can read it.
             */
            this.xor.handle(request, response, csrfToken);
            /*
             * Force eager loading of the token so the XSRF-TOKEN cookie is
             * written on the very first response (important for ngrok flows where
             * the first page load must set the cookie before JS reads it).
             */
            csrfToken.get();
        }

        @Override
        public String resolveCsrfTokenValue(HttpServletRequest request,
                                            CsrfToken csrfToken) {
            /*
             * If the request has a X-XSRF-TOKEN header (from JavaScript fetch),
             * use the plain handler which reads it unencoded from that header.
             * Otherwise fall back to the Xor handler (form parameter, already encoded).
             */
            String headerValue = request.getHeader("X-XSRF-TOKEN");
            if (headerValue != null && !headerValue.isBlank()) {
                return this.plain.resolveCsrfTokenValue(request, csrfToken);
            }
            return this.xor.resolveCsrfTokenValue(request, csrfToken);
        }
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                .csrfTokenRequestHandler(new SpaCsrfTokenRequestHandler())
            )
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(
                    "/", "/welcome", "/login", "/signup",
                    "/favicon.ico", "/error",
                    "/css/**", "/js/**", "/images/**", "/webjars/**",
                    "/terms", "/privacy"
                ).permitAll()
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/chat-page", true)
                .failureUrl("/login?error")
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID", "XSRF-TOKEN")
                .permitAll()
            );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}