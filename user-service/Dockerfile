# Use the official OpenJDK base image
FROM openjdk:17

ENV SPRING_PROFILES_ACTIVE=docker

# Set the working directory in the container
WORKDIR /app

# Copy the Spring Boot JAR file into the container
COPY build/libs/user-service-0.0.1-SNAPSHOT.jar .

# Expose the port on which the microservice will run
EXPOSE 8085

# Define the command to run the microservice
CMD ["java", "-jar", "user-service-0.0.1-SNAPSHOT.jar"]

