#################################################
# Build container image using Maven and Java 17
#################################################
FROM maven:3.9.4-eclipse-temurin-21 AS builder


# Set working directory inside the container
WORKDIR /project

# Copy Maven descriptor
COPY pom.xml /project/

# Preload dependencies to optimize build
RUN mvn dependency:go-offline

# Copy the rest of the project source code
COPY src /project/src

# Package the application without running tests
RUN mvn clean package -DskipTests

#################################################
# Runtime container with only the built JAR file
#################################################
FROM eclipse-temurin:21-jdk

# Set working directory for the final container
WORKDIR /usr/app

# Copy the built JAR from the builder container
COPY --from=builder /project/target/*.jar app.jar

# Expose Spring Boot default port
EXPOSE 8443

# Run the application when the container starts
ENTRYPOINT ["java", "-jar", "app.jar"]
