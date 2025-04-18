package com.example.demo.controller;

import com.example.demo.config.Format;
import com.example.demo.config.PaymentReportConfig;
import com.example.demo.config.Theme;
import com.example.demo.dto.PaymentReportRequest;
import com.example.demo.model.PaymentData;
import com.example.demo.service.PaymentReportService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/**
 * Controlador REST que expone los endpoints para la generación de reportes de pago
 */
@RestController
@RequestMapping("/api/reports")
public class PaymentReportController {

    private final PaymentReportService paymentReportService;

    @Autowired
    public PaymentReportController(PaymentReportService paymentReportService) {
        this.paymentReportService = paymentReportService;
    }

    /**
     * Endpoint para generar un reporte de pago en PDF
     * 
     * @param request DTO con la configuración del reporte y los datos del pago
     * @return El archivo PDF como recurso descargable
     */
    @Operation(summary = "Generar un reporte de pago en PDF", description = "Genera un reporte PDF basado en la configuración y datos proporcionados")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Reporte generado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Solicitud inválida"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping("/payment")
    public ResponseEntity<Resource> generatePaymentReport(@RequestBody PaymentReportRequest request) {
        // Utilizar el patrón Builder para configurar el reporte
        PaymentReportConfig config = new PaymentReportConfig.Builder()
                .withLogo(request.isIncludeLogo())
                .withTitle(request.getTitle())
                .withPaymentDetails(request.isIncludePaymentDetails())
                .withUserInfo(request.isIncludeUserInfo())
                .withTheme(Theme.valueOf(request.getTheme()))
                .withTimestamp(request.isIncludeTimestamp())
                .withFooterMessage(request.getFooterMessage())
                .withFormat(Format.valueOf(request.getFormat()))
                .build();

        // Crear objeto PaymentData a partir de la solicitud
        PaymentData paymentData = new PaymentData(
                request.getTransactionId(),
                request.getAmount(),
                request.getPaymentMethod(),
                request.getCustomerName()
        );

        // Generar el PDF y devolverlo como recurso
        Resource pdfResource = paymentReportService.generatePdfReport(config, paymentData);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"payment-report.pdf\"")
                .body(pdfResource);
    }
    
    @RestController
    @RequestMapping("/api/test")
    public class TestController {
        
        @GetMapping("/hello")
        public ResponseEntity<String> hello() {
            return ResponseEntity.ok("API funcionando correctamente");
        }
    }
}