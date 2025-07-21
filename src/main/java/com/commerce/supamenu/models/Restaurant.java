package com.commerce.supamenu.models;

import com.commerce.supamenu.audit.InitiatorAudit;
import com.commerce.supamenu.enums.ERestaurantCategory;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "restaurants", indexes = {
        @Index(columnList = "resto_name"),
        @Index(columnList = "category")
})
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Restaurant extends InitiatorAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ERestaurantCategory category;

    @NotBlank
    @Size(max = 255)
    @Column(name = "address", nullable = false)
    private String address;

    @NotBlank
    @Size(max = 100)
    @Column(name = "resto_name", nullable = false, length = 100)
    private String restoName;

    @Size(max = 150)
    @Column(name = "resto_full_name", length = 150)
    private String restoFullName;

    @NotNull
    @Column(name = "start_time", nullable = false)
    private LocalTime startTime;

    @NotNull
    @Column(name = "end_time", nullable = false)
    private LocalTime endTime;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("restaurant-photos")
    private List<Photo> photos;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false)
    @JsonBackReference("client-restaurants")
    private Client client;

    @JsonManagedReference("restaurant-menu")
    @OneToOne(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true)
    private Menu menu;
}