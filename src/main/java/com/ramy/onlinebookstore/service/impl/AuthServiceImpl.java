package com.ramy.onlinebookstore.service.impl;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.ramy.onlinebookstore.dto.request.LoginRequest;
import com.ramy.onlinebookstore.dto.request.RegisterRequest;
import com.ramy.onlinebookstore.dto.response.LoginResponse;
import com.ramy.onlinebookstore.dto.response.RegisterResponse;
import com.ramy.onlinebookstore.entity.User;
import com.ramy.onlinebookstore.entity.enums.UserRole;
import com.ramy.onlinebookstore.repository.UserRepository;
import com.ramy.onlinebookstore.security.JwtUtil;
import com.ramy.onlinebookstore.service.AuthService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public LoginResponse login(LoginRequest request) {
        User user = userRepo.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid credentials");
        }

        String token = jwtUtil.generateToken(user.getEmail());

        return LoginResponse.builder().token(token).build();
    }

    @Override
    public RegisterResponse register(RegisterRequest request) {
        if (userRepo.existsByEmail(request.getEmail())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already registered!");
        }

        String hashedPassword = passwordEncoder.encode(request.getPassword());

        User user = User.builder()
                .email(request.getEmail())
                .password(hashedPassword)
                .name(request.getName())
                .role(UserRole.USER)
                .build();

        userRepo.save(user);

        return RegisterResponse.builder()
                .email(user.getEmail())
                .name(user.getName())
                .build();
    }

}
