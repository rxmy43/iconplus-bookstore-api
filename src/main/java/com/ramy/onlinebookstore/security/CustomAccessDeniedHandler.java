package com.ramy.onlinebookstore.security;

import java.io.IOException;

import com.ramy.onlinebookstore.constant.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ramy.onlinebookstore.dto.response.ApiResponse;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private final ObjectMapper objectMapper;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
            AccessDeniedException accessDeniedException) throws IOException, ServletException {
       ApiResponse<Object> apiResponse = ApiResponse.builder()
               .success(false)
               .message("Forbidden : You don't have permission to access this resource")
               .code(HttpServletResponse.SC_FORBIDDEN)
               .build();
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType(MediaType.APPLICATION_JSON);
        response.getWriter().write(objectMapper.writeValueAsString(apiResponse));
                
    }

}
