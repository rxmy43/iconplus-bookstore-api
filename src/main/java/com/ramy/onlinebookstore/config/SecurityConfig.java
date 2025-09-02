package com.ramy.onlinebookstore.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import com.ramy.onlinebookstore.entity.enums.UserRole;
import com.ramy.onlinebookstore.security.JwtAuthenticationFilter;

import lombok.AllArgsConstructor;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

        @Bean
        public PasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder();
        }

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http,
                        JwtAuthenticationFilter jwtAuthenticationFilter) throws Exception {
                http
                                .csrf(csrf -> csrf.disable())
                                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                                .cors(cors -> cors
                                                .configurationSource(request -> {
                                                        var corsConfig = new CorsConfiguration();
                                                        corsConfig.setAllowedOriginPatterns(java.util.List.of("*"));
                                                        corsConfig.setAllowedMethods(List.of("GET", "POST", "PUT",
                                                                        "DELETE", "OPTIONS"));
                                                        corsConfig.setAllowedHeaders(List.of("*"));
                                                        corsConfig.setAllowCredentials(true);
                                                        return corsConfig;
                                                }))
                                .authorizeHttpRequests(auth -> auth
                                                .requestMatchers("/swagger-ui/**").permitAll()
                                                .requestMatchers("/api/auth/**").permitAll()

                                                // /categories
                                                .requestMatchers("/api/categories/**")
                                                .hasAnyRole(UserRole.ADMIN.toString())

                                                .anyRequest().authenticated())
                                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
                return http.build();
        }
}
