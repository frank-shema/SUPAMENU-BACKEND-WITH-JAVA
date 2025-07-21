package com.commerce.supamenu;

import com.commerce.supamenu.enums.ERole;
import com.commerce.supamenu.models.Role;
import com.commerce.supamenu.repositories.IRoleRepository;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
@Slf4j
public class SupamenuApplication {
	private final IRoleRepository roleRepository;

    public SupamenuApplication(IRoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public static void main(String[] args) {
		SpringApplication.run(SupamenuApplication.class, args);
	}

	@PostConstruct
	private void init(){
		Set<ERole> roles = new HashSet<>(Set.of(ERole.ROLE_CUSTOMER, ERole.ROLE_ADMIN, ERole.ROLE_CLIENT));
		for (ERole erole : roles) {
			if (!	roleRepository.existsByRole(erole)) {
				Role role = new Role();
				role.setRole(erole);
				roleRepository.save(role);
			}
		}
		log.info("Roles created on application init: {}", roles);
	}
}
