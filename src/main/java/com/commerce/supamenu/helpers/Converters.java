package com.commerce.supamenu.helpers;

import com.commerce.supamenu.dto.responses.client.ClientResponse;
import com.commerce.supamenu.dto.responses.client.ClientSummaryResponse;
import com.commerce.supamenu.dto.responses.menu.MenuResponse;
import com.commerce.supamenu.dto.responses.menu.item.MenuItemResponse;
import com.commerce.supamenu.dto.responses.order.OrderResponse;
import com.commerce.supamenu.dto.responses.order.OrderSummaryResponse;
import com.commerce.supamenu.dto.responses.order.item.OrderItemResponse;
import com.commerce.supamenu.dto.responses.order.item.OrderItemSummaryResponse;
import com.commerce.supamenu.dto.responses.photo.PhotoResponse;
import com.commerce.supamenu.dto.responses.restaurant.RestaurantResponse;
import com.commerce.supamenu.dto.responses.restaurant.RestaurantSummaryResponse;
import com.commerce.supamenu.dto.responses.role.RoleResponse;
import com.commerce.supamenu.dto.responses.user.UserResponse;
import com.commerce.supamenu.dto.responses.user.UserSummaryResponse;
import com.commerce.supamenu.models.*;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Converters {
    public static UserResponse convertToUserResponse(User user) {
        if (user == null) return null;

        UserResponse userResponse = new UserResponse();
        userResponse.setId(user.getId());
        userResponse.setFirstName(user.getFirstName());
        userResponse.setLastName(user.getLastName());
        userResponse.setEmail(user.getEmail());
        userResponse.setPhoneNumber(user.getPhoneNumber());
        userResponse.setOrders(convertToOrderSummaryResponses(user.getOrders()));
        userResponse.setRole(convertToRoleResponse(user.getRole()));
        return userResponse;
    }

    public static UserSummaryResponse convertToUserSummaryResponse(User user) {
        if (user == null) return null;

        UserSummaryResponse userSummaryResponse = new UserSummaryResponse();
        userSummaryResponse.setId(user.getId());
        userSummaryResponse.setFirstName(user.getFirstName());
        userSummaryResponse.setLastName(user.getLastName());
        userSummaryResponse.setEmail(user.getEmail());
        return userSummaryResponse;
    }

    public static OrderResponse convertToOrderResponse(CustomerOrder order) {
        if (order == null) return null;

        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setId(order.getId());
        orderResponse.setUser(convertToUserSummaryResponse(order.getUser()));
        orderResponse.setRestaurant(convertToRestaurantSummaryResponse(order.getRestaurant()));
        orderResponse.setItems(convertToOrderItemResponses(order.getItems()));
        orderResponse.setStatus(order.getStatus());
        orderResponse.setCreatedAt(order.getCreatedAt());
        return orderResponse;
    }

    public static OrderSummaryResponse convertToOrderSummaryResponse(CustomerOrder order) {
        if (order == null) return null;

        OrderSummaryResponse summary = new OrderSummaryResponse();
        summary.setId(order.getId());
        summary.setRestaurantName(order.getRestaurant().getRestoName());
        summary.setItemCount(order.getItems().size());
        summary.setTotalAmount(calculateOrderTotal(order.getItems()));
        summary.setStatus(order.getStatus());
        return summary;
    }

    private static BigDecimal calculateOrderTotal(Set<OrderItem> items) {
        return items.stream()
                .map(item -> BigDecimal.valueOf(item.getPrice()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public static OrderItemResponse convertToOrderItemResponse(OrderItem item) {
        if (item == null) return null;

        OrderItemResponse response = new OrderItemResponse();
        response.setId(item.getId());
        response.setItemName(item.getItemName());
        response.setPrice(item.getPrice());
        response.setCategory(item.getCategory());
        response.setQuantity(item.getQuantity());
        return response;
    }

    public static OrderItemSummaryResponse convertToOrderItemSummaryResponse(OrderItem item) {
        if (item == null) return null;

        OrderItemSummaryResponse summary = new OrderItemSummaryResponse();
        summary.setId(item.getId());
        summary.setItemName(item.getItemName());
        summary.setPrice(item.getPrice());
        return summary;
    }

    public static RestaurantResponse convertToRestaurantResponse(Restaurant restaurant) {
        if (restaurant == null) return null;

        RestaurantResponse response = new RestaurantResponse();
        response.setId(restaurant.getId());
        response.setCategory(restaurant.getCategory());
        response.setAddress(restaurant.getAddress());
        response.setRestoName(restaurant.getRestoName());
        response.setRestoFullName(restaurant.getRestoFullName());
        response.setStartTime(restaurant.getStartTime());
        response.setEndTime(restaurant.getEndTime());
        response.setPhotos(convertToPhotoResponses(restaurant.getPhotos()));
        response.setClient(convertToClientSummaryResponse(restaurant.getClient()));
        return response;
    }

    public static ClientResponse convertToClientResponse(Client client) {
        if (client == null) return null;

        ClientResponse response = new ClientResponse();
        response.setId(client.getId());
        response.setClientName(client.getClientName());
        response.setRepresentative(client.getRepresentative());
        response.setBankAccount(client.getBankAccount());
        response.setRestaurants(convertToRestaurantSummaryResponses(client.getRestaurants()));
        return response;
    }

    public static ClientSummaryResponse convertToClientSummaryResponse(Client client) {
        if (client == null) return null;

        ClientSummaryResponse summary = new ClientSummaryResponse();
        summary.setId(client.getId());
        summary.setClientName(client.getClientName());
        return summary;
    }

    public static RestaurantSummaryResponse convertToRestaurantSummaryResponse(Restaurant restaurant) {
        if (restaurant == null) return null;

        RestaurantSummaryResponse summary = new RestaurantSummaryResponse();
        summary.setId(restaurant.getId());
        summary.setRestoName(restaurant.getRestoName());
        summary.setCategory(restaurant.getCategory());
        return summary;
    }

    public static RoleResponse convertToRoleResponse(Role role) {
        if (role == null) return null;

        RoleResponse response = new RoleResponse();
        response.setId(role.getId());
        response.setRole(role.getRole());
        return response;
    }

    // For converting collections of entities
    public static List<OrderResponse> convertToOrderResponses(Collection<CustomerOrder> orders) {
        return orders.stream()
                .map(Converters::convertToOrderResponse)
                .collect(Collectors.toList());
    }

    public static Set<RestaurantSummaryResponse> convertToRestaurantSummaryResponses(Collection<Restaurant> restaurants) {
        return restaurants.stream()
                .map(Converters::convertToRestaurantSummaryResponse)
                .collect(Collectors.toSet());
    }

    public static List<PhotoResponse> convertToPhotoResponses(Collection<Photo> photos) {
        return photos.stream()
                .map(Converters::convertToPhotoResponse)
                .collect(Collectors.toList());
    }

    public static Set<OrderSummaryResponse> convertToOrderSummaryResponses(Collection<CustomerOrder> orders) {
        return orders.stream()
                .map(Converters::convertToOrderSummaryResponse)
                .collect(Collectors.toSet());
    }

    public static Set<OrderItemResponse> convertToOrderItemResponses(Collection<OrderItem> items) {
        return items.stream()
                .map(Converters::convertToOrderItemResponse)
                .collect(Collectors.toSet());
    }

    public static MenuResponse convertToMenuResponse(Menu menu) {
        if (menu == null) return null;

        MenuResponse response = new MenuResponse();
        response.setId(menu.getId());
        response.setRestaurant(convertToRestaurantSummaryResponse(menu.getRestaurant()));
        response.setItems(convertToMenuItemResponses(menu.getItems()));
        response.setCreatedAt(menu.getCreatedAt());
        response.setUpdatedAt(menu.getUpdatedAt());
        return response;
    }

    public static MenuItemResponse convertToMenuItemResponse(MenuItem item) {
        if (item == null) return null;

        MenuItemResponse response = new MenuItemResponse();
        response.setId(item.getId());
        response.setName(item.getName());
        response.setPrice(item.getPrice());
        response.setCategory(item.getCategory());
        response.setDescription(item.getDescription());
        response.setPhoto(convertToPhotoResponse(item.getPhoto()));
        return response;
    }

    // Supporting conversion methods
    private static Set<MenuItemResponse> convertToMenuItemResponses(Collection<MenuItem> items) {
        return items.stream()
                .map(Converters::convertToMenuItemResponse)
                .collect(Collectors.toSet());
    }

    // Photo conversion (already exists)
    public static PhotoResponse convertToPhotoResponse(Photo photo) {
        if (photo == null) return null;

        PhotoResponse response = new PhotoResponse();
        response.setId(photo.getId());
        response.setUrl(photo.getUrl());
        return response;
    }
}
