package com.commerce.supamenu.dto.requests.order;

import com.commerce.supamenu.dto.requests.order.item.OrderItemRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.Set;
import java.util.UUID;

public class OrderRequest {
    @NotNull(message = "User ID is required")
    private UUID userId;

    @NotNull(message = "Restaurant ID is required")
    private UUID restaurantId;

    @Valid
    @NotEmpty(message = "Order items cannot be empty")
    private Set<OrderItemRequest> items;
}
