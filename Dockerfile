# Build stage
FROM maven:3.9-eclipse-temurin-21-alpine AS build
WORKDIR /workspace/app

# 1. Cachear dependencias Maven (layer caching)
COPY pom.xml .
RUN mvn dependency:go-offline -B

# 2. Copiar código y compilar
COPY src src
RUN mvn package -DskipTests -B

# 3. Extraer layers de Spring Boot (desde 3.x)
RUN java -Djarmode=layertools -jar target/*.jar extract --destination extracted

# Run stage
FROM eclipse-temurin:21-jre-alpine

# 4. Seguridad: no correr como root
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

WORKDIR /app

# 5. Copiar por layers (mejor cache de Docker)
COPY --from=build /workspace/app/extracted/dependencies/ ./
COPY --from=build /workspace/app/extracted/spring-boot-loader/ ./
COPY --from=build /workspace/app/extracted/snapshot-dependencies/ ./
COPY --from=build /workspace/app/extracted/application/ ./

EXPOSE 8080

# 6. Entrypoint usando el launcher de Spring Boot
ENTRYPOINT ["java", "org.springframework.boot.loader.launch.JarLauncher"]
