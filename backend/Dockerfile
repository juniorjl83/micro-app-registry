# Use a Gradle image for the build stage
FROM gradle:7-jdk21-alpine AS builder
WORKDIR /app

# Copy only the build files first to leverage layer caching
COPY build.gradle.kts settings.gradle.kts .
COPY src ./src

# Build the Spring Boot fat jar
RUN gradle bootJar --no-daemon

# Runtime image with just the JRE
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=builder /app/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]
