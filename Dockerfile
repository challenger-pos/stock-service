FROM maven:3.10.1-eclipse-temurin-21 AS builder
WORKDIR /workspace
COPY . /workspace
RUN mvn -B -DskipTests package

FROM eclipse-temurin:21-jre AS runtime
WORKDIR /app
COPY --from=builder /workspace/infrastructure/target/*.jar /app/stock-service.jar

EXPOSE 8080

ENTRYPOINT ["java", "-Xms512M", "-Xmx1024M", "-jar", "/app/stock-service.jar"]
