package com.commerce.supamenu.dto.requests.restaurant;

import com.commerce.supamenu.dto.requests.photo.PhotoRequest;
import com.commerce.supamenu.enums.ERestaurantCategory;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Data
public class RestaurantRequest {
    @NotNull(message = "Category is required")
    private ERestaurantCategory category;

    @NotBlank(message = "Address is required")
    @Size(max = 255, message = "Address cannot exceed 255 characters")
    private String address;

    @NotBlank(message = "Restaurant name is required")
    @Size(max = 100, message = "Restaurant name cannot exceed 100 characters")
    private String restoName;

    @Size(max = 150, message = "Restaurant full name cannot exceed 150 characters")
    private String restoFullName;

    @NotNull(message = "Opening time is required")
    private LocalTime startTime;

    @NotNull(message = "Closing time is required")
    private LocalTime endTime;

    @NotNull(message = "Client ID is required")
    private UUID clientId;

    @Valid
    private List<PhotoRequest> photos;
}
