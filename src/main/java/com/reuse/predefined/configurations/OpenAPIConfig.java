package com.reuse.predefined.configurations;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class OpenAPIConfig {

        @Value("${common.openapi.dev-url}")
        private String prodUrl;

        @Value("${common.openapi.dev-url}")
        private String devUrl;

        String schemeName = "bearerAuth";
        String bearerFormat = "JWT";
        String scheme = "bearer";

        @Bean
        public OpenAPI myOpenAPI() {
                Server devServer = new Server();
                devServer.setUrl(devUrl);
                devServer.setDescription("Server URL in Development environment");

                Server prodServer = new Server();
                prodServer.setUrl(prodUrl);
                prodServer.setDescription("Server URL in Production environment");

                Contact contact = new Contact();
                contact.setEmail("abhishekasafpur@gmail.com");
                contact.setName("Model for initial setup");
                contact.setUrl("");

                License mitLicense = new License().name("MIT License").url("https://choosealicense.com/licenses/mit/");

                Info info = new Info()
                                .title("Initial setup")
                                .version("1.0")
                                .contact(contact)
                                .description("This API exposes endpoints to manage video portal.")
                                .termsOfService("https://www.video.com/terms")
                                .license(mitLicense);

                return new OpenAPI().info(info).servers(List.of(devServer, prodServer))
                                .addSecurityItem(new SecurityRequirement()
                                                .addList(schemeName))
                                .components(new Components()
                                                .addSecuritySchemes(
                                                                schemeName, new SecurityScheme()
                                                                                .name(schemeName)
                                                                                .type(SecurityScheme.Type.HTTP)
                                                                                .bearerFormat(bearerFormat)
                                                                                .in(SecurityScheme.In.HEADER)
                                                                                .scheme(scheme)));
        }

}