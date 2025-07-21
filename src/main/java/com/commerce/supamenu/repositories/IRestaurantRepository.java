package com.commerce.supamenu.repositories;

import com.commerce.supamenu.models.Restaurant;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface IRestaurantRepository extends JpaRepository<Restaurant, UUID> {
    Optional<Restaurant> findByIdAndClientId(@NotNull(message = "Restaurant ID is required") UUID restaurantId, UUID clientId);
}
