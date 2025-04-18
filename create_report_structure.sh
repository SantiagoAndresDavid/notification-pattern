#!/bin/bash

# Script adaptado para crear la estructura de carpetas del proyecto Payment Report System
# en la ruta específica del proyecto existente
# Autor: Claude
# Fecha: 18 de abril de 2025

BASE_DIR="/workspaces/notification-pattern/src/main/java/com/example/demo"

echo "Creando estructura de carpetas para Payment Report System en $BASE_DIR..."

# Verificar si la ruta base existe
if [ ! -d "$BASE_DIR" ]; then
    echo "Error: El directorio base $BASE_DIR no existe."
    echo "Por favor, asegúrate de que la ruta es correcta o créala primero."
    exit 1
fi

# Estructura de directorios para el patrón de reportes
mkdir -p "$BASE_DIR/report/config"
mkdir -p "$BASE_DIR/report/generator"
mkdir -p "$BASE_DIR/report/model"
mkdir -p "$BASE_DIR/report/demo"

# Crear archivos Java básicos
touch "$BASE_DIR/report/config/PaymentReportConfig.java"
touch "$BASE_DIR/report/config/Format.java"
touch "$BASE_DIR/report/config/Theme.java"
touch "$BASE_DIR/report/generator/PaymentReportGenerator.java"
touch "$BASE_DIR/report/model/PaymentData.java"
touch "$BASE_DIR/report/demo/PaymentReportBuilderDemo.java"

# Crear directorio para recursos específicos de reportes (en caso de necesitarlos)
mkdir -p "/workspaces/notification-pattern/src/main/resources/report/templates"
mkdir -p "/workspaces/notification-pattern/src/main/resources/report/images"
mkdir -p "/workspaces/notification-pattern/src/main/resources/report/fonts"

# Crear archivos de recursos de ejemplo
touch "/workspaces/notification-pattern/src/main/resources/report/templates/report-template.html"
touch "/workspaces/notification-pattern/src/main/resources/report/images/company-logo.png"
touch "/workspaces/notification-pattern/src/main/resources/report/fonts/default-font.ttf"

# Crear directorio para tests
mkdir -p "/workspaces/notification-pattern/src/test/java/com/example/demo/report/config"
mkdir -p "/workspaces/notification-pattern/src/test/java/com/example/demo/report/generator"

# Crear archivos de prueba
touch "/workspaces/notification-pattern/src/test/java/com/example/demo/report/config/PaymentReportConfigTest.java"
touch "/workspaces/notification-pattern/src/test/java/com/example/demo/report/generator/PaymentReportGeneratorTest.java"

echo "Estructura de carpetas creada exitosamente en $BASE_DIR."

# Mostrar la estructura creada usando el comando find
echo -e "\nEstructura de directorios creada:"
find "$BASE_DIR/report" -type d | sort | sed -e "s/[^-][^\/]*\//  |/g" -e "s/|\([^ ]\)/|-\1/"

echo -e "\nArchivos creados:"
find "$BASE_DIR/report" -type f | sort