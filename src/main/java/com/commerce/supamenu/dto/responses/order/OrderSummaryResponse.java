package com.commerce.supamenu.dto.responses.order;

import com.commerce.supamenu.enums.EOrderStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class OrderSummaryResponse {
    private UUID id;
    private String restaurantName;
    private int itemCount;
    private BigDecimal totalAmount;
    private EOrderStatus status;
}
