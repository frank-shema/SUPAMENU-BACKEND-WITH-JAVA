package com.commerce.supamenu.config;

import java.util.Optional;

import com.commerce.supamenu.security.user.UserSecurityDetails;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;


@Configuration
@EnableJpaAuditing
public class AuditConfig {
@Bean
    public AuditorAware<String> auditProvider() {
        return new SpringSecurityAuditAwareImpl();
    }
}

class SpringSecurityAuditAwareImpl implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()
                || authentication instanceof AnonymousAuthenticationToken) {
            return Optional.empty();
        }

        UserSecurityDetails userSecurityDetails = (UserSecurityDetails) authentication.getPrincipal();

        return Optional.ofNullable(userSecurityDetails.getUsername());

    }
}
