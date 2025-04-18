package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.servers.Server;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        // Obtener el nombre del Codespace del entorno
        String codespaceName = System.getenv("CODESPACE_NAME");
        
        OpenAPI openAPI = new OpenAPI()
                .info(new io.swagger.v3.oas.models.info.Info()
                        .title("API de Reportes de Pago")
                        .version("1.0")
                        .description("API para generar reportes de pago personalizables mediante patrón Builder"));

        // Si estamos en un codespace, configurar la URL del servidor correctamente
        if (codespaceName != null && !codespaceName.isEmpty()) {
            // URL pública del Codespace para el puerto 9090
            String serverUrl = "https://" + codespaceName + "-9090.app.github.dev";
            
            Server server = new Server();
            server.setUrl(serverUrl);
            server.setDescription("URL del Codespace");
            
            openAPI.setServers(List.of(server));
        }
        
        return openAPI;
    }
}