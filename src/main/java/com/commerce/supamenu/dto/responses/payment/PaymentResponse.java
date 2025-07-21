package com.commerce.supamenu.dto.responses.payment;

import com.commerce.supamenu.dto.responses.order.OrderSummaryResponse;
import com.commerce.supamenu.dto.responses.restaurant.RestaurantSummaryResponse;
import com.commerce.supamenu.dto.responses.user.UserSummaryResponse;
import com.commerce.supamenu.enums.EPaymentMethod;

import java.time.LocalDateTime;
import java.util.UUID;

public class PaymentResponse {
    private UUID id;
    private Boolean isCredit;
    private EPaymentMethod method;
    private UserSummaryResponse user;
    private RestaurantSummaryResponse restaurant;
    private OrderSummaryResponse order;
    private LocalDateTime paymentDate;
}
