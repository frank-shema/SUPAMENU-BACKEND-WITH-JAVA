package com.commerce.supamenu.dto.responses.order.item;

import lombok.Data;

import java.util.UUID;

@Data
public class OrderItemSummaryResponse {
    private UUID id;
    private String itemName;
    private double price;
}