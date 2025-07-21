package com.commerce.supamenu.repositories;

import com.commerce.supamenu.enums.ERole;
import com.commerce.supamenu.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface IRoleRepository extends JpaRepository<Role, UUID> {
    Optional<Role> findByRole(ERole role);

    boolean existsByRole(ERole erole);
}
