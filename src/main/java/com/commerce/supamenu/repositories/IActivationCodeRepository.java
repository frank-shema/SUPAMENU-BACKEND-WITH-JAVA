package com.commerce.supamenu.repositories;

import com.commerce.supamenu.models.ActivationCode;
import com.commerce.supamenu.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface IActivationCodeRepository extends JpaRepository<ActivationCode, UUID> {
    Optional<ActivationCode> findByCode(String code);

    Optional<ActivationCode> findByCodeAndUser(String activationCode, User user);
}
