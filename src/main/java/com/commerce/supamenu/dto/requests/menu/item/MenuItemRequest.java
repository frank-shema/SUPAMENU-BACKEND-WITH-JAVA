package com.commerce.supamenu.dto.requests.menu.item;

import com.commerce.supamenu.enums.EItemCategory;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.UUID;

@Data
public class MenuItemRequest {
    @NotNull(message = "Category is required")
    private EItemCategory category;

    @NotBlank(message = "Name is required")
    @Size(max = 100, message = "Name cannot exceed 100 characters")
    private String name;

    @NotNull(message = "Price is required")
    @Positive(message = "Price must be positive")
    @Digits(integer = 6, fraction = 2, message = "Price must have up to 6 integer and 2 fraction digits")
    private Double price;

    @Size(max = 500, message = "Description cannot exceed 500 characters")
    private String description;

    private UUID photoId;
}
