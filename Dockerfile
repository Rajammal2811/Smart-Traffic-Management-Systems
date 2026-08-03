# Multi-stage Docker build for Smart Traffic Management System

# Build stage
FROM maven:3.8.4-openjdk-17 AS builder
WORKDIR /app

# Copy only what is needed for a Maven build
COPY pom.xml ./
COPY src src

# Build artifact (skip tests for faster local builds; tests should be run separately)
RUN mvn -B -DskipTests clean package

# Runtime stage
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app

# Install curl for health checks (minimal)
RUN apt-get update && apt-get install -y --no-install-recommends curl && rm -rf /var/lib/apt/lists/*

# Copy built jar from builder stage
COPY --from=builder /app/target/*.jar app.jar

# Create logs directory
RUN mkdir -p /app/logs

EXPOSE 8080

# Use actuator health endpoint
HEALTHCHECK --interval=30s --timeout=10s --start-period=10s --retries=3 \
  CMD curl -f http://localhost:8080/actuator/health || exit 1

ENTRYPOINT ["java","-jar","/app/app.jar"]
