# Stage 1: Build the project with Gradle
FROM gradle:7.0.2-jdk11 AS build

# Set the working directory
WORKDIR /app

# Copy the project files
COPY build.gradle settings.gradle /app/
COPY src /app/src

# Build the project
RUN gradle build --no-daemon

# Stage 2: Create the final image to run the application
FROM openjdk:11-jre-slim

# Set the working directory
WORKDIR /app

# Copy the built jar from the build stage
COPY --from=build /app/build/libs/*.jar app.jar

# Expose the application port
EXPOSE 8080

# Set the entry point to run the jar
ENTRYPOINT ["java", "-jar", "app.jar"]
