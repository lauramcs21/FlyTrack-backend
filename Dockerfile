# ===== STAGE 1: Build =====
FROM maven:3.9-eclipse-temurin-17 AS builder
WORKDIR /app

# Copiar solo archivos de dependencias primero (cache de capas)
COPY pom.xml .
COPY src ./src

# Compilar la aplicación
RUN mvn clean package -DskipTests

# ===== STAGE 2: Runtime ligero =====
FROM eclipse-temurin:17-jre-slim AS runner
WORKDIR /app

# Crear usuario no-root para seguridad
RUN groupadd -r spring && useradd -r -g spring spring
USER spring:spring

# Copiar solo el JAR compilado desde el stage builder
COPY --from=builder --chown=spring:spring /app/target/*.jar app.jar

# Health check
HEALTHCHECK --interval=30s --timeout=10s --start-period=40s --retries=3 \
  CMD curl -f http://localhost:8080/actuator/health || exit 1

# Exponer puerto
EXPOSE 8080

# Ejecutar con perfil de producción
ENTRYPOINT ["java", \
  "-Djava.security.egd=file:/dev/./urandom", \
  "-Dspring.profiles.active=prod", \
  "-XX:+UseContainerSupport", \
  "-XX:MaxRAMPercentage=75.0", \
  "-jar", "app.jar"]