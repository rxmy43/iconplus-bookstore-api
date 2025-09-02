package com.ramy.onlinebookstore.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.ramy.onlinebookstore.entity.User;
import com.ramy.onlinebookstore.entity.enums.UserRole;
import com.ramy.onlinebookstore.repository.UserRepository;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if (!userRepo.existsByEmail("admin@bookstore.com")) {
            User admin = User.builder()
                    .name("Admin Bookstore")
                    .email("admin@bookstore.com")
                    .password(passwordEncoder.encode("admin123"))
                    .role(UserRole.ADMIN)
                    .build();

            userRepo.save(admin);
            System.out.println("Admin user created : admin@bookstore.com / admin123");
        }
    }

}
