package com.commerce.supamenu.services;

import com.commerce.supamenu.dto.requests.menu.MenuRequest;
import com.commerce.supamenu.dto.responses.ApiResponse;
import com.commerce.supamenu.dto.responses.menu.MenuResponse;

import java.util.UUID;

public interface IMenuService {
    ApiResponse<MenuResponse> createMenu(MenuRequest request, UUID clientId);
    public ApiResponse<MenuResponse> getMenuByRestaurant(UUID restaurantId, UUID clientId);
}
