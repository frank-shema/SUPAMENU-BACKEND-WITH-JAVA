package com.commerce.supamenu.dto.requests.payment;

import com.commerce.supamenu.enums.EPaymentMethod;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public class PaymentRequest {
    @NotNull(message = "Order ID is required")
    private UUID orderId;

    @NotNull(message = "Payment method is required")
    private EPaymentMethod method;

    @NotNull(message = "Credit flag is required")
    private Boolean isCredit;
}
