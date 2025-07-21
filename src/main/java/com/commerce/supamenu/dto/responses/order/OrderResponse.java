package com.commerce.supamenu.dto.responses.order;

import com.commerce.supamenu.dto.responses.order.item.OrderItemResponse;
import com.commerce.supamenu.dto.responses.restaurant.RestaurantSummaryResponse;
import com.commerce.supamenu.dto.responses.user.UserSummaryResponse;
import com.commerce.supamenu.enums.EOrderStatus;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Data
public class OrderResponse {
    private UUID id;
    private UserSummaryResponse user;
    private RestaurantSummaryResponse restaurant;
    private Set<OrderItemResponse> items;
    private EOrderStatus status;
    private LocalDateTime createdAt;
}
