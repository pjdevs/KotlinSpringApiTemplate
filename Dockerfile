# Stage 1: Build
FROM gradle:9.3-jdk25-alpine AS builder

WORKDIR /app

# Copy gradle files
COPY build.gradle.kts settings.gradle.kts gradle.properties ./
COPY api/build.gradle.kts ./api/
COPY domain/build.gradle.kts ./domain/
COPY infra/build.gradle.kts ./infra/

# Copy source
COPY api/src ./api/src
COPY domain/src ./domain/src
COPY infra/src ./infra/src

# Build
RUN gradle :api:bootJar --no-daemon

# Stage 2: Runtime
FROM eclipse-temurin:25-jre-alpine

WORKDIR /app

# Copy JAR from builder
COPY --from=builder /app/api/build/libs/api-*.jar app.jar

# Expose port
EXPOSE 8080

# Run
ENTRYPOINT ["java", "-jar", "app.jar"]