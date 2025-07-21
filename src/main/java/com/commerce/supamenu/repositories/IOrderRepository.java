package com.commerce.supamenu.repositories;

import com.commerce.supamenu.models.CustomerOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IOrderRepository extends JpaRepository<CustomerOrder, UUID> {
}
