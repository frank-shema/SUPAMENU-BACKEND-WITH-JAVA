package com.commerce.supamenu.security.handlers;

import com.commerce.supamenu.dto.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {

        ApiResponse<String> apiResponse = new ApiResponse<>(
                false,
                "Please log in to perform this action.",
                "Authentication Failed",
                LocalDateTime.now().toString(), null);

        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json");

        PrintWriter writer = response.getWriter();
        writer.write(apiResponse.toString());
        writer.flush();
    }
}