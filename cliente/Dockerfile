# Etapa 1: Compilación (Hará la construcción usando todo el Monorepo)
FROM maven:3.8.8-eclipse-temurin-17 AS build
WORKDIR /app

# Copiar absolutamente todo el contenido del repositorio (así Maven encuentra todos los módulos)
COPY . .

# Compilar única y exclusivamente el microservicio de clientes saltando los tests
RUN mvn clean package -pl cliente -am -DskipTests

# Etapa 2: Imagen de ejecución ligera (Esta es la que corre en producción)
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Copiar únicamente el archivo JAR generado desde la etapa anterior
COPY --from=build /app/cliente/target/cliente-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8084
ENTRYPOINT ["java", "-jar", "app.jar"]