# Use a lightweight base image with Java runtime
FROM eclipse-temurin:21-jdk-alpine

# Set the working directory inside the container
WORKDIR /app

# Copy the application JAR file into the container
COPY build/libs/node-0.0.1-SNAPSHOT.jar app.jar

# Expose the port your Spring Boot app runs on (default is 8080)
EXPOSE $SERVER_PORT

# Run the Spring Boot application
ENTRYPOINT ["java", "-jar", "app.jar"]
