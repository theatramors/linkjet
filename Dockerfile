# Build stage with JDK
FROM maven:3.9.9-eclipse-temurin-21-jammy AS builder
WORKDIR /app
COPY pom.xml .
# Cache dependencies
#RUN mvn dependency:go-offline -B
COPY src ./src
# Build with Java 21
RUN mvn package -DskipTests

# Runtime stage with JRE
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app
# Copy built artifact
COPY --from=builder /app/target/*.jar app.jar
# Security setup
RUN groupadd spring && useradd -g spring spring \
    && chown -R spring:spring /app \
    && chmod -R 750 /app
USER spring
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]