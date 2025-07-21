package com.commerce.supamenu.dto.requests.filters;

import com.commerce.supamenu.enums.ERestaurantCategory;

public class RestaurantFilterRequest {
    private String name;
    private ERestaurantCategory category;
    private String location;
    private Boolean openNow;
}
