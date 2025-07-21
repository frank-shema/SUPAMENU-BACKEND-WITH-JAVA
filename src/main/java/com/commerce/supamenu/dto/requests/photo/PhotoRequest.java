package com.commerce.supamenu.dto.requests.photo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PhotoRequest {
    @NotBlank(message = "URL is required")
    private String url;
}
