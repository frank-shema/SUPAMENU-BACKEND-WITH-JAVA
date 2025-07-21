package com.commerce.supamenu.serviceImpls;

import com.commerce.supamenu.dto.requests.menu.MenuRequest;
import com.commerce.supamenu.dto.requests.menu.item.MenuItemRequest;
import com.commerce.supamenu.dto.responses.ApiResponse;
import com.commerce.supamenu.dto.responses.menu.MenuResponse;
import com.commerce.supamenu.exceptions.NotFoundException;
import com.commerce.supamenu.helpers.Converters;
import com.commerce.supamenu.models.Menu;
import com.commerce.supamenu.models.MenuItem;
import com.commerce.supamenu.models.Restaurant;
import com.commerce.supamenu.repositories.IMenuItemRepository;
import com.commerce.supamenu.repositories.IMenuRepository;
import com.commerce.supamenu.repositories.IRestaurantRepository;
import com.commerce.supamenu.services.IMenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements IMenuService {
    private final IRestaurantRepository restaurantRepository;
    private final IMenuRepository menuRepository;
    private final IMenuItemRepository menuItemRepository;

    @Override
    public ApiResponse<MenuResponse> createMenu(MenuRequest request, UUID clientId) {
        Restaurant restaurant = restaurantRepository.findByIdAndClientId(request.getRestaurantId(), clientId)
                .orElseThrow(() -> new NotFoundException("Restaurant not found or doesn't belong to client"));

        // Create menu
        Menu menu = new Menu();
        menu.setRestaurant(restaurant);
        Menu savedMenu = menuRepository.save(menu);

        // Create menu items
        Set<MenuItem> items = request.getItems().stream()
                .map(itemRequest -> createMenuItem(itemRequest, savedMenu))
                .collect(Collectors.toSet());

        savedMenu.setItems(items);
        menuRepository.save(savedMenu);

        return ApiResponse.success(
                "Menu created successfully",
                Converters.convertToMenuResponse(savedMenu)
        );
    }

    private MenuItem createMenuItem(MenuItemRequest request, Menu menu) {
        MenuItem item = new MenuItem();
        item.setName(request.getName());
        item.setPrice(request.getPrice());
        item.setCategory(request.getCategory());
        item.setDescription(request.getDescription());
        item.setMenu(menu);
        return menuItemRepository.save(item);
    }

    @Override
    public ApiResponse<MenuResponse> getMenuByRestaurant(UUID restaurantId, UUID clientId) {
        Restaurant restaurant = restaurantRepository.findByIdAndClientId(restaurantId, clientId)
                .orElseThrow(() -> new NotFoundException("Restaurant not found or doesn't belong to client"));

        Menu menu = menuRepository.findByRestaurant(restaurant)
                .orElseThrow(() -> new NotFoundException("Menu not found"));

        return ApiResponse.success(
                "Menu retrieved successfully",
                Converters.convertToMenuResponse(menu)
        );
    }
}
