package com.commerce.supamenu.dto.responses.invoice;

import com.commerce.supamenu.dto.responses.payment.PaymentReceiptResponse;
import com.commerce.supamenu.dto.responses.restaurant.RestaurantSummaryResponse;
import com.commerce.supamenu.dto.responses.user.UserSummaryResponse;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class InvoiceResponse {
    private UUID id;
    private UserSummaryResponse user;
    private RestaurantSummaryResponse restaurant;
    private PaymentReceiptResponse payment;
    private BigDecimal totalAmount;
    private LocalDateTime invoiceDate;
    private String invoiceNumber;
}
