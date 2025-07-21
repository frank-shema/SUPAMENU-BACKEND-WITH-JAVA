package com.commerce.supamenu.dto.requests.order.status;

import com.commerce.supamenu.enums.EOrderStatus;
import jakarta.validation.constraints.NotNull;

public class OrderStatusRequest {
    @NotNull(message = "Status is required")
    private EOrderStatus status;
}
