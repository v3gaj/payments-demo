# payments-demo

Java 21 + Spring Boot 3.3. Strategy + Registry para múltiples métodos de pago.

## 1. Instalar JDK 21 (solo una vez)

Windows (PowerShell):   winget install EclipseAdoptium.Temurin.21.JDK
macOS (Homebrew):       brew install --cask temurin@21
Ubuntu/Debian:          sudo apt install openjdk-21-jdk

Cierra y abre de nuevo la terminal/VS Code y verifica:  java -version

No necesitas instalar Maven: el proyecto trae Maven Wrapper (mvnw).

## 2. Abrir en VS Code
- Abre la carpeta payments-demo.
- Acepta instalar las extensiones recomendadas (Extension Pack for Java, Spring Boot, REST Client).

## 3. Ejecutar
Terminal integrada:
    Windows:      .\mvnw.cmd test        y       .\mvnw.cmd spring-boot:run
    macOS/Linux:  ./mvnw test            y       ./mvnw spring-boot:run

La primera vez descarga Maven y las dependencias (unos minutos).

## 4. Probar
- Swagger UI:   http://localhost:8080/swagger-ui.html
- Health:       http://localhost:8080/actuator/health
- requests.http (extensión REST Client)

## Agregar un método de pago
Valor en PaymentMethodType + record en model (y en @JsonSubTypes y permits) + @Component Processor.
