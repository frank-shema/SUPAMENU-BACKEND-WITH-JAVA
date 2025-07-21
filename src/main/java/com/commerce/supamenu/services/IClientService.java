package com.commerce.supamenu.services;

import com.commerce.supamenu.dto.requests.client.ClientRequest;
import com.commerce.supamenu.dto.requests.restaurant.RestaurantRequest;
import com.commerce.supamenu.dto.requests.user.UserUpdateRequest;
import com.commerce.supamenu.dto.responses.ApiResponse;
import com.commerce.supamenu.dto.responses.client.ClientResponse;
import com.commerce.supamenu.dto.responses.client.ClientSummaryResponse;

import java.util.UUID;

public interface IClientService {
    ApiResponse<ClientSummaryResponse> createClient(ClientRequest request);
    ApiResponse<ClientResponse> getClient(UUID clientId);
    ApiResponse<String>  activateClient(UUID clientId, String activationCode);
    ApiResponse<String> completeClientSetup(UUID clientId, String password,
                                            UserUpdateRequest userUpdate,
                                            RestaurantRequest restaurantRequest);
}
