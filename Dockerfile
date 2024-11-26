# Stage 1: Build the JAR file
FROM openjdk:17-jdk AS build

# Set the working directory inside the container
WORKDIR /app

# Copy the project files into the container
COPY . .

# Install necessary tools for Gradle
RUN apt-get update && apt-get install -y bash curl unzip && rm -rf /var/lib/apt/lists/*

# Make the Gradle wrapper executable
RUN chmod +x ./gradlew

# Build the Spring Boot application
RUN ./gradlew bootJar --no-daemon

# Stage 2: Run the application
FROM openjdk:17-jdk-alpine

# Add a volume to store logs
VOLUME /tmp

# Copy the JAR file from the build stage to the runtime stage
COPY --from=build /app/build/libs/Clinics-0.0.1-SNAPSHOT.jar app.jar

# Expose the port that the Spring Boot app listens on
EXPOSE 8090

# Command to run the Spring Boot application
ENTRYPOINT ["java", "-jar", "/app.jar"]