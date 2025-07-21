package com.commerce.supamenu.repositories;

import com.commerce.supamenu.models.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IMenuItemRepository extends JpaRepository<MenuItem, UUID> {
}
