package com.commerce.supamenu.dto.requests.client;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ClientRequest {
    @NotBlank(message = "Client name is required")
    @Size(max = 100, message = "Client name cannot exceed 100 characters")
    private String clientName;

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;

    @Size(max = 100, message = "Representative name cannot exceed 100 characters")
    private String representative;

    @Size(max = 50, message = "Bank account cannot exceed 50 characters")
    @Pattern(regexp = "^[A-Z]{2}[0-9]{2}[A-Z0-9]{1,30}$", message = "Invalid IBAN format")
    private String bankAccount;
}
