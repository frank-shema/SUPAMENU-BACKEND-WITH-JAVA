package com.commerce.supamenu.dto.responses.menu.item;

import com.commerce.supamenu.dto.responses.photo.PhotoResponse;
import com.commerce.supamenu.enums.EItemCategory;
import lombok.Data;

import java.util.UUID;

@Data
public class MenuItemResponse {
    private UUID id;
    private EItemCategory category;
    private String name;
    private Double price;
    private String description;
    private PhotoResponse photo;
}
