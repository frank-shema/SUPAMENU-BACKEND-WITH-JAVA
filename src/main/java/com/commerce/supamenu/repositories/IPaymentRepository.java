package com.commerce.supamenu.repositories;

import com.commerce.supamenu.models.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IPaymentRepository extends JpaRepository<Payment, UUID> {
}
