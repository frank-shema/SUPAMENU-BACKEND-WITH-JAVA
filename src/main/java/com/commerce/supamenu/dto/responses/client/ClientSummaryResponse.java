package com.commerce.supamenu.dto.responses.client;

import lombok.Data;

import java.util.UUID;

@Data
public class ClientSummaryResponse {
    private UUID id;
    private String clientName;
}
