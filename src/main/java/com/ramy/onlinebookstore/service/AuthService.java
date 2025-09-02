package com.ramy.onlinebookstore.service;

import com.ramy.onlinebookstore.dto.request.LoginRequest;
import com.ramy.onlinebookstore.dto.request.RegisterRequest;
import com.ramy.onlinebookstore.dto.response.LoginResponse;
import com.ramy.onlinebookstore.dto.response.RegisterResponse;

public interface AuthService {
    /**
     * Registers a new user in the system.
     *
     * @param RegisterRequest request the registration details containing user
     *                        information
     * @return RegisterResponse response containing the registration result and user
     *         details
     */
    RegisterResponse register(RegisterRequest request);

    /**
     * Authenticates a user and provides access credentials.
     *
     * @param LoginRequest request the login credentials including email and
     *                     password
     * @return LoginResponse response containing authentication status and JWT
     *         token
     */
    LoginResponse login(LoginRequest request);
}
