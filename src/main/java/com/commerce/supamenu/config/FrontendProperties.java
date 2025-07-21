package com.commerce.supamenu.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "frontend")
@Data
public class FrontendProperties {
    private String clientActivationUrl;
    private String passwordResetUrl;
    private String loginUrl;
    private String clientDashboardUrl;
}
