package com.commerce.supamenu.dto.responses;

import java.time.LocalDateTime;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ErrorResponse {
    private boolean success;
    private String error;
    private String message;
    private String timestamp;
    private Map<String, String> details;

    public ErrorResponse(String error, String message, Map<String, String> details) {
        this.success = false;
        this.error = error;
        this.message = message;
        this.details = details;
        this.timestamp = LocalDateTime.now().toString();
    }

    public ErrorResponse(String error, String message) {
        this.success = false;
        this.error = error;
        this.message = message;
        this.timestamp = LocalDateTime.now().toString();
    }
}