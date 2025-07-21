package com.commerce.supamenu.controllers;

import com.commerce.supamenu.dto.requests.auth.ChangePasswordRequest;
import com.commerce.supamenu.dto.requests.auth.LoginRequest;
import com.commerce.supamenu.dto.responses.ApiResponse;
import com.commerce.supamenu.dto.responses.auth.AuthResponse;
import com.commerce.supamenu.services.IAuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthenticationController {
    private final IAuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

    @PutMapping("/change-password")
    public ResponseEntity<ApiResponse<String>> changePassword(@Valid @RequestBody ChangePasswordRequest request) {
        return ResponseEntity.ok(authenticationService.changePassword(request));
    }
}
