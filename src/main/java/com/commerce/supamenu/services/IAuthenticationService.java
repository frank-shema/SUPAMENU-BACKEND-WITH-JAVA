package com.commerce.supamenu.services;

import com.commerce.supamenu.dto.requests.auth.ChangePasswordRequest;
import com.commerce.supamenu.dto.requests.auth.LoginRequest;
import com.commerce.supamenu.dto.responses.ApiResponse;
import com.commerce.supamenu.dto.responses.auth.AuthResponse;

public interface IAuthenticationService {
    ApiResponse<AuthResponse> authenticate(LoginRequest req);
    ApiResponse<String> changePassword(ChangePasswordRequest req);
}
