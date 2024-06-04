package com.riwi.Simulacrum_SpringBoot_Test.api.config;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@Configuration
@OpenAPIDefinition(
    info = @Info(
        title = "API para administrar entidades de un Curso (registro, curso, lecciones, tareas, entregas, mensajes)",
        version = "1.0",
        description = "Documentacion API de administrar entidades de un Curso"
    ))
public class OpenApiConfig {
    
}
