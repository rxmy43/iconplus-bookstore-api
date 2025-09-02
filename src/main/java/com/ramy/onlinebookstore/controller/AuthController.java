package com.ramy.onlinebookstore.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ramy.onlinebookstore.annotation.ResponseSuccessMessage;
import com.ramy.onlinebookstore.constant.MediaType;
import com.ramy.onlinebookstore.dto.request.LoginRequest;
import com.ramy.onlinebookstore.dto.request.RegisterRequest;
import com.ramy.onlinebookstore.dto.response.LoginResponse;
import com.ramy.onlinebookstore.dto.response.RegisterResponse;
import com.ramy.onlinebookstore.service.AuthService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "User login", description = "Authenticates a user and returns JWT access token for accessing protected endpoints")
    @ApiResponse(responseCode = "200", description = "Login successful", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = LoginResponse.class)))
    @PostMapping("/login")
    @ResponseSuccessMessage("Login successful!")
    @ResponseStatus(HttpStatus.OK)
    public LoginResponse login(@Valid @RequestBody LoginRequest request) {
        return authService.login(request);
    }

    @Operation(summary = "User registration", description = "Create new user account")
    @ApiResponse(responseCode = "200", description = "Register successful", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = RegisterResponse.class)))
    @PostMapping("/register")
    @ResponseSuccessMessage("Register successful!")
    public RegisterResponse register(@Valid @RequestBody RegisterRequest request) {
        return authService.register(request);
    }
}
