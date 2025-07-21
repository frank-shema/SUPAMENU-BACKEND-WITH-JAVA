package com.commerce.supamenu.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("*");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Serve static resources from multiple locations
        registry.addResourceHandler("/**")
                .addResourceLocations(
                        "classpath:/static/",
                        "classpath:/static",
                        "classpath:/certificates/",
                        "classpath:/META-INF/resources/"
                );

        // Specific configuration for certificates
        registry.addResourceHandler("/certificates/**")
                .addResourceLocations("classpath:/static/certificates/")
                .setCachePeriod(3600);  // Cache for 1 hour

        // Fallback for PDF files
        registry.addResourceHandler("*.pdf")
                .addResourceLocations("classpath:/static/certificates/");
    }
}