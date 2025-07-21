package com.commerce.supamenu.models;

import com.commerce.supamenu.enums.EClientStatus;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "clients", indexes = @Index(columnList = "client_name"))
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @NotBlank(message = "Client name is required")
    @Column(name = "client_name", nullable = false)
    private String clientName;

    @Column(name = "email")
    private String email;

    @Column(name = "representative")
    private String representative;

    @Column(name = "bank_accnt")
    private String bankAccount;

    @Enumerated(EnumType.STRING)
    private EClientStatus status;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("client-restaurants")
    private Set<Restaurant> restaurants;
}