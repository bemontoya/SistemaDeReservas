# Etapa 1: Compilación
FROM maven:3.8.8-eclipse-temurin-17 AS build
WORKDIR /app

# 1. Copiar el POM padre desde la raíz del proyecto para que Maven resuelva la herencia
COPY pom.xml .

# 2. Copiar el subproyecto 'cliente' completo
COPY cliente ./cliente

# 3. Compilar específicamente el módulo de clientes usando el POM padre
RUN mvn clean package -pl cliente -am -DskipTests

# Etapa 2: Imagen de ejecución ligera
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Copiar el JAR generado dentro del módulo de clientes
COPY --from=build /app/cliente/target/cliente-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8084
ENTRYPOINT ["java", "-jar", "app.jar"]