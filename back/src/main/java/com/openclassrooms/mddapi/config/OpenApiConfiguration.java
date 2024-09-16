package com.openclassrooms.mddapi.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.tags.Tag;

@Configuration
public class OpenApiConfiguration {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
        // Configuration
        .components(new Components()
            // Define the security scheme
            .addSecuritySchemes("bearerAuth", new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
            )
        )
        // Apply the security defined
        .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
        .info(new Info()
            .title("Chatop API Documentation")
            .description("Swagger documentation for the API MDD app.")
            .version("0.9.9") // Version de l'API
        )
        .tags(List.of(
            new Tag().name("Authentication").description("Operations for logging or registering a user"),
            new Tag().name("Comment").description("Post comments"),
            new Tag().name("Post").description("Operations about posts"),
            new Tag().name("Topic").description("Operations about topics")
        ));
    }
}
