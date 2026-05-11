# Build stage
FROM maven:3.9.2-eclipse-temurin-22 as builder

WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline -B

COPY src ./src

RUN mvn clean package -DskipTests

# Runtime stage
FROM eclipse-temurin:22-jdk-slim

WORKDIR /app

COPY --from=builder /app/target/barberpoint-bff-*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
