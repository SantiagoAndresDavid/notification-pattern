package com.example.demo.config;
/**
 * Clase inmutable que representa la configuración para la generación de reportes de pago PDF.
 * Utiliza el patrón Builder para permitir configuraciones fluidas.
 */
public class PaymentReportConfig {
    private final boolean includeLogo;
    private final String title;
    private final boolean includePaymentDetails;
    private final boolean includeUserInfo;
    private final Theme theme;
    private final boolean includeTimestamp;
    private final String footerMessage;
    private final Format format;

    /**
     * Constructor privado que solo puede ser llamado por el Builder
     */
    private PaymentReportConfig(Builder builder) {
        this.includeLogo = builder.includeLogo;
        this.title = builder.title;
        this.includePaymentDetails = builder.includePaymentDetails;
        this.includeUserInfo = builder.includeUserInfo;
        this.theme = builder.theme;
        this.includeTimestamp = builder.includeTimestamp;
        this.footerMessage = builder.footerMessage;
        this.format = builder.format;
    }

    // Getters para todas las propiedades
    public boolean isIncludeLogo() {
        return includeLogo;
    }

    public String getTitle() {
        return title;
    }

    public boolean isIncludePaymentDetails() {
        return includePaymentDetails;
    }

    public boolean isIncludeUserInfo() {
        return includeUserInfo;
    }

    public Theme getTheme() {
        return theme;
    }

    public boolean isIncludeTimestamp() {
        return includeTimestamp;
    }

    public String getFooterMessage() {
        return footerMessage;
    }

    public Format getFormat() {
        return format;
    }

    @Override
    public String toString() {
        return "PaymentReportConfig{" +
                "includeLogo=" + includeLogo +
                ", title='" + title + '\'' +
                ", includePaymentDetails=" + includePaymentDetails +
                ", includeUserInfo=" + includeUserInfo +
                ", theme=" + theme +
                ", includeTimestamp=" + includeTimestamp +
                ", footerMessage='" + footerMessage + '\'' +
                ", format=" + format +
                '}';
    }

    /**
     * Clase Builder que permite configurar fluida y dinámicamente los reportes
     */
    public static class Builder {
        // Valores por defecto para todos los campos
        private boolean includeLogo = false;
        private String title = "Reporte de Pago";
        private boolean includePaymentDetails = true;
        private boolean includeUserInfo = true;
        private Theme theme = Theme.LIGHT;
        private boolean includeTimestamp = true;
        private String footerMessage = "";
        private Format format = Format.A4;

        /**
         * Constructor por defecto
         */
        public Builder() {
        }

        /**
         * Configura si se debe incluir el logo de la empresa en el reporte
         * 
         * @param includeLogo true para incluir el logo, false para omitirlo
         * @return la instancia del Builder para encadenar llamadas
         */
        public Builder withLogo(boolean includeLogo) {
            this.includeLogo = includeLogo;
            return this;
        }

        /**
         * Configura el título del reporte
         * 
         * @param title el título a mostrar
         * @return la instancia del Builder para encadenar llamadas
         */
        public Builder withTitle(String title) {
            this.title = title;
            return this;
        }

        /**
         * Configura si se deben incluir los detalles del pago
         * 
         * @param includePaymentDetails true para incluir detalles, false para omitirlos
         * @return la instancia del Builder para encadenar llamadas
         */
        public Builder withPaymentDetails(boolean includePaymentDetails) {
            this.includePaymentDetails = includePaymentDetails;
            return this;
        }

        /**
         * Configura si se debe incluir información del usuario
         * 
         * @param includeUserInfo true para incluir información, false para omitirla
         * @return la instancia del Builder para encadenar llamadas
         */
        public Builder withUserInfo(boolean includeUserInfo) {
            this.includeUserInfo = includeUserInfo;
            return this;
        }

        /**
         * Configura el tema de colores del reporte
         * 
         * @param theme el tema a utilizar (LIGHT o DARK)
         * @return la instancia del Builder para encadenar llamadas
         */
        public Builder withTheme(Theme theme) {
            this.theme = theme;
            return this;
        }

        /**
         * Configura si se debe incluir la fecha y hora de generación
         * 
         * @param includeTimestamp true para incluir fecha y hora, false para omitirlas
         * @return la instancia del Builder para encadenar llamadas
         */
        public Builder withTimestamp(boolean includeTimestamp) {
            this.includeTimestamp = includeTimestamp;
            return this;
        }

        /**
         * Configura el mensaje a mostrar en el pie del reporte
         * 
         * @param footerMessage el mensaje a mostrar
         * @return la instancia del Builder para encadenar llamadas
         */
        public Builder withFooterMessage(String footerMessage) {
            this.footerMessage = footerMessage;
            return this;
        }

        /**
         * Configura el formato del papel del reporte
         * 
         * @param format el formato a utilizar (A4 o LETTER)
         * @return la instancia del Builder para encadenar llamadas
         */
        public Builder withFormat(Format format) {
            this.format = format;
            return this;
        }

        /**
         * Construye una configuración de reporte inmutable con los valores configurados
         * 
         * @return una instancia de PaymentReportConfig con la configuración establecida
         */
        public PaymentReportConfig build() {
            return new PaymentReportConfig(this);
        }
    }
}