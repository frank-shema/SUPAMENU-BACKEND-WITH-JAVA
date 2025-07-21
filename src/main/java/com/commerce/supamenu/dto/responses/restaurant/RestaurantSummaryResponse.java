package com.commerce.supamenu.dto.responses.restaurant;

import com.commerce.supamenu.enums.ERestaurantCategory;
import lombok.Data;

import java.util.UUID;

@Data
public class RestaurantSummaryResponse {
    private UUID id;
    private String restoName;
    private ERestaurantCategory category;
}
