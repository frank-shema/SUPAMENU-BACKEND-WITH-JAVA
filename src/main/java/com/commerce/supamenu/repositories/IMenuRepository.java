package com.commerce.supamenu.repositories;

import com.commerce.supamenu.models.Menu;
import com.commerce.supamenu.models.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface IMenuRepository extends JpaRepository<Menu, UUID> {
    Optional<Menu> findByRestaurant(Restaurant restaurant);
}
