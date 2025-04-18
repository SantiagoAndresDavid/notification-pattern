package com.example.demo.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO (Data Transfer Object) para recibir las solicitudes de generación de reportes
 * desde la API REST
 */
@Getter
@Setter
public class PaymentReportRequest {
    
    // Opciones de configuración del reporte
    private boolean includeLogo = false;
    
    @NotBlank(message = "El título del reporte es obligatorio")
    private String title = "Reporte de Pago";
    
    private boolean includePaymentDetails = true;
    private boolean includeUserInfo = true;
    
    @NotBlank(message = "El tema es obligatorio")
    private String theme = "LIGHT";
    
    private boolean includeTimestamp = true;
    private String footerMessage = "";
    
    @NotBlank(message = "El formato es obligatorio")
    private String format = "A4";
    
    // Datos del pago
    @NotBlank(message = "El ID de transacción es obligatorio")
    private String transactionId;
    
    @NotNull(message = "El monto es obligatorio")
    @Positive(message = "El monto debe ser positivo")
    private Double amount;
    
    @NotBlank(message = "El método de pago es obligatorio")
    private String paymentMethod;
    
    @NotBlank(message = "El nombre del cliente es obligatorio")
    private String customerName;
}