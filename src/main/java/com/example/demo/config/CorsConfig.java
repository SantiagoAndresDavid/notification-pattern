package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        
        // Permitir solicitudes desde cualquier origen
        config.addAllowedOriginPattern("*");
        
        // Permitir todos los encabezados
        config.addAllowedHeader("*");
        
        // Permitir todos los métodos
        config.addAllowedMethod("*");
        
        // Habilitar cookies y encabezados de autenticación (si es necesario)
        config.setAllowCredentials(true);
        
        // Exponer todos los encabezados a los clientes
        config.addExposedHeader("Content-Disposition");
        
        // Aplicar esta configuración a todas las rutas
        source.registerCorsConfiguration("/**", config);
        
        return new CorsFilter(source);
    }
}