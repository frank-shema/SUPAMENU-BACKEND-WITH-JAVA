package com.commerce.supamenu.dto.responses.restaurant;

import com.commerce.supamenu.dto.responses.client.ClientSummaryResponse;
import com.commerce.supamenu.dto.responses.menu.MenuSummaryResponse;
import com.commerce.supamenu.dto.responses.photo.PhotoResponse;
import com.commerce.supamenu.enums.ERestaurantCategory;
import lombok.Data;

import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Data
public class RestaurantResponse {
    private UUID id;
    private ERestaurantCategory category;
    private String address;
    private String restoName;
    private String restoFullName;
    private LocalTime startTime;
    private LocalTime endTime;
    private List<PhotoResponse> photos;
    private ClientSummaryResponse client;
    private MenuSummaryResponse menu;
}
