# Use JDK 21 to build the Spring Boot jar
FROM eclipse-temurin:21-jdk-alpine AS builder
WORKDIR /app

# Copy Gradle wrapper and scripts first (cache dependencies)
COPY gradlew .
COPY gradle ./gradle
COPY build.gradle.kts settings.gradle.kts .
COPY src ./src

# Build the fat jar
RUN ./gradlew bootJar --no-daemon

# Runtime image with just JRE
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=builder /app/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]
