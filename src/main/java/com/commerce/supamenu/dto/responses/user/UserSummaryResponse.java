package com.commerce.supamenu.dto.responses.user;

import lombok.Data;

import java.util.UUID;

@Data
public class UserSummaryResponse {
    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
}
