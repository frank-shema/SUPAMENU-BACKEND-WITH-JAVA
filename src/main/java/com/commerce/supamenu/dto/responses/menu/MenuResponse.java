package com.commerce.supamenu.dto.responses.menu;

import com.commerce.supamenu.dto.responses.menu.item.MenuItemResponse;
import com.commerce.supamenu.dto.responses.restaurant.RestaurantSummaryResponse;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Data
public class MenuResponse {
    private UUID id;
    private RestaurantSummaryResponse restaurant;
    private Set<MenuItemResponse> items;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
