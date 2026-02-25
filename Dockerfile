FROM eclipse-temurin:21-jdk AS builder
WORKDIR /workspace
# Copy sources (we build only the infrastructure module and its dependencies)
COPY . /workspace
# Use the project's Maven Wrapper to build (avoids depending on docker hub maven image tags)
RUN chmod +x mvnw && ./mvnw -B -DskipTests -am -pl infrastructure package --no-transfer-progress

FROM eclipse-temurin:21-jre AS runtime
WORKDIR /app
# Copy the fat jar produced by the Spring Boot plugin
COPY --from=builder /workspace/infrastructure/target/stock-service.jar /app/stock-service.jar

# JVM defaults (can be overridden at runtime with JAVA_OPTS)
ENV JAVA_OPTS="-Xms512m -Xmx1024m"

EXPOSE 8080

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /app/stock-service.jar"]
