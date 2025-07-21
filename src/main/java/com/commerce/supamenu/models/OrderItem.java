package com.commerce.supamenu.models;

import com.commerce.supamenu.audit.InitiatorAudit;
import com.commerce.supamenu.enums.EItemCategory;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "order_items")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItem extends InitiatorAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @NotBlank
    @Size(max = 100)
    @Column(name = "item_name", nullable = false, length = 100)
    private String itemName;

    @Positive
    @Column(name = "price", nullable = false)
    private double price;

    @Positive
    @Column(name = "quantity", nullable = false)
    private int quantity;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EItemCategory category;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    @JsonBackReference("order-items")
    private CustomerOrder order;
}