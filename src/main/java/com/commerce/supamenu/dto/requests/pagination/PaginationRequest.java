package com.commerce.supamenu.dto.requests.pagination;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.hibernate.query.SortDirection;

public class PaginationRequest {
    @Min(value = 1, message = "Page number must be at least 1")
    private int page = 1;

    @Min(value = 1, message = "Page size must be at least 1")
    @Max(value = 100, message = "Page size cannot exceed 100")
    private int size = 10;

    private String sortBy;
    private SortDirection sortDirection = SortDirection.ASCENDING;
}
