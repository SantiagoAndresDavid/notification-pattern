package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Excepción personalizada para los errores durante la generación de reportes
 */
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class ReportGenerationException extends RuntimeException {
    
    /**
     * Constructor con mensaje de error
     * 
     * @param message Mensaje descriptivo del error
     */
    public ReportGenerationException(String message) {
        super(message);
    }
    
    /**
     * Constructor con mensaje de error y causa
     * 
     * @param message Mensaje descriptivo del error
     * @param cause Excepción que causó el error
     */
    public ReportGenerationException(String message, Throwable cause) {
        super(message, cause);
    }
}