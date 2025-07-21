package com.commerce.supamenu.dto.responses.menu.item;

import com.commerce.supamenu.enums.EItemCategory;

import java.util.UUID;

public class MenuItemSummaryResponse {
    private UUID id;
    private String name;
    private Double price;
    private EItemCategory category;
}
