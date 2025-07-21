package com.commerce.supamenu.dto.requests.order.item;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public class OrderItemRequest {
    @NotNull(message = "Menu item ID is required")
    private UUID menuItemId;

    @NotNull(message = "Quantity is required")
    @Min(value = 1, message = "Quantity must be at least 1")
    private Integer quantity;

    @Size(max = 200, message = "Special instructions cannot exceed 200 characters")
    private String specialInstructions;
}
