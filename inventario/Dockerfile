FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

COPY inventario/target/inventario-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8087

ENTRYPOINT ["java", "-jar", "app.jar"]