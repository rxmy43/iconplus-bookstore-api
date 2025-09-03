package com.ramy.onlinebookstore.controller.advice;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

import com.ramy.onlinebookstore.dto.response.ApiResponse;

@ControllerAdvice
public class GlobalExceptionHandler {

    // 4xx client errors
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ApiResponse<Object>> handleClientError(ResponseStatusException ex) {
        ApiResponse<Object> response = ApiResponse.builder()
                .success(false)
                .message(ex.getReason() != null ? ex.getReason() : "Client error")
                .code(ex.getStatusCode().value())
                .errors(ex.getMessage())
                .build();
        return new ResponseEntity<>(response, ex.getStatusCode());
    }

    // Form validation errors (422)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Object>> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, String> details = new HashMap<>();
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            details.put(fieldError.getField(), fieldError.getDefaultMessage());
        }

        ApiResponse<Object> response = ApiResponse.builder()
                .success(false)
                .message("Form validation error")
                .code(HttpStatus.UNPROCESSABLE_ENTITY.value())
                .fieldErrors(details)
                .build();

        return new ResponseEntity<>(response, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    // Generic server errors (5xx)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> handleServerError(Exception ex) {
        ApiResponse<Object> response = ApiResponse.builder()
                .success(false)
                .message("Internal server error")
                .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .errors(ex.getMessage())
                .build();
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
