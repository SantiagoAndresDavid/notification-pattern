package com.example.demo.generator;

import com.example.demo.config.Format;
import com.example.demo.config.PaymentReportConfig;
import com.example.demo.config.Theme;
import com.example.demo.model.PaymentData;
import com.example.demo.exception.ReportGenerationException;
import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import com.lowagie.text.pdf.draw.LineSeparator;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * Clase encargada de generar los reportes PDF según la configuración
 * y los datos proporcionados
 */
@Component
public class PaymentReportGenerator {

    private static final String COMPANY_LOGO_PATH = "report/images/company-logo.png";
    private static final Color LIGHT_THEME_COLOR = new Color(255, 255, 255);
    private static final Color DARK_THEME_COLOR = new Color(50, 50, 50);
    private static final Color LIGHT_TEXT_COLOR = new Color(0, 0, 0);
    private static final Color DARK_TEXT_COLOR = new Color(255, 255, 255);
    
    /**
     * Genera un PDF según la configuración y los datos de pago proporcionados
     * 
     * @param config La configuración del reporte
     * @param paymentData Los datos del pago
     * @return ByteArrayOutputStream con el contenido del PDF generado
     */
    public ByteArrayOutputStream generatePDF(PaymentReportConfig config, PaymentData paymentData) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        
        try {
            // Configurar el documento según el formato especificado
            Rectangle pageSize = getPageSize(config.getFormat());
            Document document = new Document(pageSize, 50, 50, 50, 50);
            PdfWriter writer = PdfWriter.getInstance(document, outputStream);
            
            document.open();
            
            // Aplicar tema (colores de fondo y texto)
            applyTheme(document, writer, config.getTheme());
            
            // Agregar logo si está habilitado
            if (config.isIncludeLogo()) {
                addLogo(document);
            }
            
            // Agregar título
            addTitle(document, config.getTitle(), config.getTheme());
            
            // Agregar información de pago
            if (config.isIncludePaymentDetails()) {
                addPaymentDetails(document, paymentData, config.getTheme());
            }
            
            // Agregar información del usuario
            if (config.isIncludeUserInfo()) {
                addUserInfo(document, paymentData, config.getTheme());
            }
            
            // Agregar timestamp si está habilitado
            if (config.isIncludeTimestamp()) {
                addTimestamp(document, config.getTheme());
            }
            
            // Agregar mensaje de pie de página si existe
            if (config.getFooterMessage() != null && !config.getFooterMessage().isEmpty()) {
                addFooterMessage(document, config.getFooterMessage(), config.getTheme());
            }
            
            document.close();
            return outputStream;
            
        } catch (DocumentException | IOException e) {
            throw new ReportGenerationException("Error al generar el PDF: " + e.getMessage(), e);
        }
    }
    
    private Rectangle getPageSize(Format format) {
        return format == Format.A4 ? PageSize.A4 : PageSize.LETTER;
    }
    
    private void applyTheme(Document document, PdfWriter writer, Theme theme) {
        // Aplicar color de fondo según el tema
        PdfContentByte canvas = writer.getDirectContentUnder();
        canvas.saveState();
        canvas.setColorFill(theme == Theme.LIGHT ? LIGHT_THEME_COLOR : DARK_THEME_COLOR);
        canvas.rectangle(0, 0, document.getPageSize().getWidth(), document.getPageSize().getHeight());
        canvas.fill();
        canvas.restoreState();
    }
    
    private void addLogo(Document document) throws DocumentException, IOException {
        try {
            byte[] logoBytes = new ClassPathResource(COMPANY_LOGO_PATH).getInputStream().readAllBytes();
            Image logo = Image.getInstance(logoBytes);
            logo.scaleToFit(150, 150);
            logo.setAlignment(Element.ALIGN_CENTER);
            document.add(logo);
            document.add(Chunk.NEWLINE);
        } catch (IOException e) {
            // Si no se puede cargar el logo, continuar sin él
            document.add(new Paragraph("Logo no disponible"));
        }
    }
    
    private void addTitle(Document document, String title, Theme theme) throws DocumentException {
        Font titleFont = new Font(Font.HELVETICA, 18, Font.BOLD, 
                theme == Theme.LIGHT ? LIGHT_TEXT_COLOR : DARK_TEXT_COLOR);
        Paragraph titleParagraph = new Paragraph(title, titleFont);
        titleParagraph.setAlignment(Element.ALIGN_CENTER);
        titleParagraph.setSpacingAfter(20);
        document.add(titleParagraph);
    }
    
    private void addPaymentDetails(Document document, PaymentData paymentData, Theme theme) throws DocumentException {
        Font headerFont = new Font(Font.HELVETICA, 14, Font.BOLD, 
                theme == Theme.LIGHT ? LIGHT_TEXT_COLOR : DARK_TEXT_COLOR);
        Font contentFont = new Font(Font.HELVETICA, 12, Font.NORMAL, 
                theme == Theme.LIGHT ? LIGHT_TEXT_COLOR : DARK_TEXT_COLOR);
        
        Paragraph header = new Paragraph("Detalles del Pago", headerFont);
        header.setSpacingBefore(15);
        header.setSpacingAfter(10);
        document.add(header);
        
        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(90);
        table.setSpacingBefore(10);
        table.setSpacingAfter(10);
        
        // Añadir filas a la tabla
        addTableRow(table, "ID de Transacción:", paymentData.getTransactionId(), contentFont);
        addTableRow(table, "Monto:", String.format(Locale.US, "%.2f", paymentData.getAmount()), contentFont);
        addTableRow(table, "Método de Pago:", paymentData.getPaymentMethod(), contentFont);
        
        document.add(table);
    }
    
    private void addUserInfo(Document document, PaymentData paymentData, Theme theme) throws DocumentException {
        Font headerFont = new Font(Font.HELVETICA, 14, Font.BOLD, 
                theme == Theme.LIGHT ? LIGHT_TEXT_COLOR : DARK_TEXT_COLOR);
        Font contentFont = new Font(Font.HELVETICA, 12, Font.NORMAL, 
                theme == Theme.LIGHT ? LIGHT_TEXT_COLOR : DARK_TEXT_COLOR);
        
        Paragraph header = new Paragraph("Información del Cliente", headerFont);
        header.setSpacingBefore(15);
        header.setSpacingAfter(10);
        document.add(header);
        
        Paragraph customerInfo = new Paragraph("Cliente: " + paymentData.getCustomerName(), contentFont);
        document.add(customerInfo);
    }
    
    private void addTimestamp(Document document, Theme theme) throws DocumentException {
        Font timestampFont = new Font(Font.HELVETICA, 10, Font.ITALIC, 
                theme == Theme.LIGHT ? LIGHT_TEXT_COLOR : DARK_TEXT_COLOR);
        
        String timestamp = "Generado el: " + 
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
        
        Paragraph timestampParagraph = new Paragraph(timestamp, timestampFont);
        timestampParagraph.setAlignment(Element.ALIGN_RIGHT);
        timestampParagraph.setSpacingBefore(20);
        document.add(timestampParagraph);
    }
    
    private void addFooterMessage(Document document, String message, Theme theme) throws DocumentException {
        Font footerFont = new Font(Font.HELVETICA, 10, Font.ITALIC, 
                theme == Theme.LIGHT ? LIGHT_TEXT_COLOR : DARK_TEXT_COLOR);
        
        document.add(Chunk.NEWLINE);
        document.add(new LineSeparator());
        
        Paragraph footer = new Paragraph(message, footerFont);
        footer.setAlignment(Element.ALIGN_CENTER);
        footer.setSpacingBefore(10);
        document.add(footer);
    }
    
    private void addTableRow(PdfPTable table, String label, String value, Font font) {
        PdfPCell labelCell = new PdfPCell(new Phrase(label, font));
        labelCell.setBorder(Rectangle.NO_BORDER);
        labelCell.setPadding(5);
        
        PdfPCell valueCell = new PdfPCell(new Phrase(value, font));
        valueCell.setBorder(Rectangle.NO_BORDER);
        valueCell.setPadding(5);
        
        table.addCell(labelCell);
        table.addCell(valueCell);
    }
}