package com.commerce.supamenu.dto.responses.client;

import com.commerce.supamenu.dto.responses.restaurant.RestaurantSummaryResponse;
import com.commerce.supamenu.enums.EClientStatus;
import lombok.Data;

import java.util.Set;
import java.util.UUID;

@Data
public class ClientResponse {
    private UUID id;
    private String clientName;
    private String representative;
    private String bankAccount;
    private EClientStatus status;
    private Set<RestaurantSummaryResponse> restaurants;
}
