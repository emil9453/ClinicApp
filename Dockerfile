# Stage 1: Build the JAR file
FROM openjdk:17-jdk-buster AS build

# Add a volume to store logs
VOLUME /tmp

# Copy the JAR file from the build directory to the Docker image
COPY Clinics-0.0.1-SNAPSHOT.jar app.jar

# Expose the port that the Spring Boot app listens on
EXPOSE 8090

# Command to run the Spring Boot application
ENTRYPOINT ["java", "-jar", "/app.jar"]
