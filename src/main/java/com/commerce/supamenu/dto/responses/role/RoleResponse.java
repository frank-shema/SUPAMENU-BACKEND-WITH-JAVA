package com.commerce.supamenu.dto.responses.role;

import com.commerce.supamenu.enums.ERole;
import lombok.Data;

import java.util.UUID;

@Data
public class RoleResponse {
    private UUID id;
    private ERole role;
}
