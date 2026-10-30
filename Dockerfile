# =============================================================================
# Stage 1: Build stage using Maven and Eclipse Temurin JDK 17
# =============================================================================
FROM maven:3.9.6-eclipse-temurin-17-alpine AS builder

WORKDIR /app

# Cache dependencies by copying pom.xml first
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy source files and build the JAR
COPY src ./src
RUN mvn clean package -DskipTests

# =============================================================================
# Stage 2: Production runtime image using Eclipse Temurin JRE 17
# =============================================================================
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Create a non-root user and group
RUN addgroup -S appgroup && adduser -S appuser -G appgroup

# Create runtime directories for logs and uploads
RUN mkdir -p /app/logs /app/uploads && chown -R appuser:appgroup /app

# Copy packaged JAR from builder stage
COPY --from=builder /app/target/*.jar app.jar
RUN chown appuser:appgroup /app/app.jar

# Switch to non-root user
USER appuser

# Expose backend port
EXPOSE 8080

# Launch Spring Boot application
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar app.jar"]
