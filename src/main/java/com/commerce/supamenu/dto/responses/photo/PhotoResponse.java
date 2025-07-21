package com.commerce.supamenu.dto.responses.photo;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class PhotoResponse {
    private UUID id;
    private String url;
    private String altText;
    private LocalDateTime uploadedAt;
}
