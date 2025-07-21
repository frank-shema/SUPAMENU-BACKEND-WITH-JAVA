package com.commerce.supamenu.dto.responses.auth;

import com.commerce.supamenu.dto.responses.user.UserSummaryResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {
    private String accessToken;
}