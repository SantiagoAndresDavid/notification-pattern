package com.example.demo.service;

import com.example.demo.config.PaymentReportConfig;
import com.example.demo.model.PaymentData;
import com.example.demo.generator.PaymentReportGenerator;
import com.example.demo.exception.ReportGenerationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Servicio encargado de coordinar la generación de reportes PDF
 */
@Service
public class PaymentReportService {
    
    private static final Logger logger = LoggerFactory.getLogger(PaymentReportService.class);
    private final PaymentReportGenerator reportGenerator;
    
    /**
     * Constructor con inyección de dependencias
     * 
     * @param reportGenerator El generador de reportes PDF
     */
    public PaymentReportService(PaymentReportGenerator reportGenerator) {
        this.reportGenerator = reportGenerator;
    }
    
    /**
     * Genera un reporte PDF basado en la configuración y los datos de pago proporcionados
     * 
     * @param config La configuración del reporte construida con el patrón Builder
     * @param paymentData Los datos del pago para incluir en el reporte
     * @return Un recurso que contiene el PDF generado
     * @throws ReportGenerationException Si ocurre algún error durante la generación
     */
    public Resource generatePdfReport(PaymentReportConfig config, PaymentData paymentData) {
        try {
            logger.info("Generando reporte PDF para el pago con ID: {}", paymentData.getTransactionId());
            
            // Registrar las opciones de configuración utilizadas
            logger.debug("Configuración del reporte: {}", config);
            
            // Generar el PDF utilizando la configuración del Builder
            ByteArrayOutputStream outputStream = reportGenerator.generatePDF(config, paymentData);
            
            // Crear un nombre de archivo con timestamp
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            String filename = "payment_report_" + paymentData.getTransactionId() + "_" + timestamp + ".pdf";
            
            logger.info("Reporte PDF generado exitosamente: {}", filename);
            
            // Devolver el PDF como un recurso
            return new ByteArrayResource(outputStream.toByteArray()) {
                @Override
                public String getFilename() {
                    return filename;
                }
            };
            
        } catch (Exception e) {
            logger.error("Error al generar el reporte PDF", e);
            throw new ReportGenerationException("Error al generar el reporte PDF: " + e.getMessage(), e);
        }
    }
    
    /**
     * Método adicional para previsualizar un reporte sin guardar
     * 
     * @param config La configuración del reporte
     * @param paymentData Los datos del pago
     * @return El recurso con el PDF generado
     */
    public Resource previewPdfReport(PaymentReportConfig config, PaymentData paymentData) {
        try {
            logger.info("Generando vista previa de reporte para pago ID: {}", paymentData.getTransactionId());
            
            // Generar el PDF de vista previa
            ByteArrayOutputStream outputStream = reportGenerator.generatePDF(config, paymentData);
            
            // Devolver el PDF como recurso temporal
            return new ByteArrayResource(outputStream.toByteArray()) {
                @Override
                public String getFilename() {
                    return "preview_payment_pdf";
                }
            };
            
        } catch (Exception e) {
            logger.error("Error al generar la vista previa del reporte", e);
            throw new ReportGenerationException("Error al generar vista previa: " + e.getMessage(), e);
        }
    }
}