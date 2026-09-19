package com.servicehub.servicehub_api.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI serviceHubOpenAPI() {

        SecurityScheme jwtScheme = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
                .name("bearerAuth");

        SecurityRequirement requirement =
                new SecurityRequirement()
                        .addList("bearerAuth");

        return new OpenAPI()
                .info(new Info()
                        .title("ServiceHub API")
                        .description("API de gerenciamento da plataforma ServiceHub")
                        .version("v1.0.0"))
                 .addSecurityItem(requirement)
                .components(
                        new Components()
                                .addSecuritySchemes(
                                        "bearerAuth",
                                        jwtScheme
                                )
                );
    }
}