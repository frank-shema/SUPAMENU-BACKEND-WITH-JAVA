package com.commerce.supamenu.dto.responses.order.item;

import com.commerce.supamenu.enums.EItemCategory;
import lombok.Data;

import java.util.UUID;

@Data
public class OrderItemResponse {
    private UUID id;
    private String itemName;
    private double price;
    private EItemCategory category;
    private int quantity;
}
