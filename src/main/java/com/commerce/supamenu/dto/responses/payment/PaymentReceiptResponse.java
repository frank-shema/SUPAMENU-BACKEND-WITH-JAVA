package com.commerce.supamenu.dto.responses.payment;

import com.commerce.supamenu.enums.EPaymentMethod;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class PaymentReceiptResponse {
    private UUID id;
    private String transactionId;
    private BigDecimal amount;
    private EPaymentMethod method;
    private LocalDateTime paymentDate;
}
