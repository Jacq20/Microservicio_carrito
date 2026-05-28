package com.carrito.Microservicio_carrito.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

// @Configuration le dice a Spring que este archivo
// tiene "herramientas" que necesita el proyecto
@Configuration
public class RestTemplateConfig {
    // @Bean crea el RestTemplate y lo deja disponible para usar en cualquier parte del proyecto
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
