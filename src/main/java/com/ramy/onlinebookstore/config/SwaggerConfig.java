package com.ramy.onlinebookstore.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class SwaggerConfig {
        @Value("${swagger.dev-url}")
        private String devUrl;

        @Value("${swagger.prod-url}")
        private String prodUrl;

        @Bean
        public OpenAPI customOpenAPI() {
                Server devServer = new Server();
                devServer.setUrl(devUrl);
                devServer.setDescription("Development Server URL");

                Server prodServer = new Server();
                prodServer.setUrl(prodUrl);
                prodServer.setDescription("Production Server URL");

                Contact contact = new Contact();
                contact.setEmail("contact@onlinebookstore.com");
                contact.setName("Online Bookstore");
                contact.setUrl("https://www.onlinebookstore.com");

                License license = new License()
                                .name("MIT License")
                                .url("https://choosealicense.com/licenses/mit/");

                Info info = new Info()
                                .title("Online Bookstore API")
                                .version("1.0.0")
                                .contact(contact)
                                .description("This API exposes endpoints for managing an online bookstore.")
                                .license(license);

                return new OpenAPI()
                                .info(info)
                                .servers(List.of(devServer, prodServer));
        }
}
