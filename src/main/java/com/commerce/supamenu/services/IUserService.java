package com.commerce.supamenu.services;

import com.commerce.supamenu.dto.requests.registration.RegisterRequest;
import com.commerce.supamenu.dto.responses.ApiResponse;
import com.commerce.supamenu.dto.responses.user.UserResponse;

public interface IUserService {
    ApiResponse<UserResponse> register(RegisterRequest req);
}
