package com.commerce.supamenu.models;

import com.commerce.supamenu.audit.InitiatorAudit;
import com.commerce.supamenu.enums.EPaymentMethod;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "payments")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Payment extends InitiatorAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "is_credit")
    private Boolean isCredit = false;

    @Column(name = "transaction_id", unique = true)
    private String transactionId;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EPaymentMethod method;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

    @OneToOne(mappedBy = "payment")
    @JsonBackReference("payment-invoice")
    private Invoice invoice;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private CustomerOrder order;
}