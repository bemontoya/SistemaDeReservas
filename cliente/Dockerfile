# Se usa directamente la imagen de ejecución
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Copiamos el JAR que ya compilaste localmente con Maven
COPY cliente/target/cliente-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8084
ENTRYPOINT ["java", "-jar", "app.jar"]