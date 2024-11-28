# Etapa de compilación
FROM maven:3.9.3-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src

# Ejecutar la compilación con el comando `mvn clean package -DskipTests`
RUN mvn clean package -DskipTests

# Etapa final: Usar una imagen base de Java
FROM openjdk:17-jdk-slim

# Usar una imagen base de Java
FROM openjdk:17-jdk-slim

# Argumento para el nombre del archivo JAR generado
ARG JAR_FILE=target/ms-accounts-trxs-0.0.1-SNAPSHOT.jar

# Copiar el archivo JAR al contenedor
COPY --from=build /app/${JAR_FILE} app.jar

# Exponer el puerto del microservicio
EXPOSE 8082

# Comando para ejecutar la aplicación
#ENTRYPOINT ["java", "-jar", "/app.jar"]
#ENTRYPOINT ["java", "-jar", "/app.jar", "--spring.datasource.url=jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;WEB_ALLOW_OTHERS=true", "--server.address=0.0.0.0"]
ENTRYPOINT ["java", "-Dh2.webAllowOthers=true", "-jar", "/app.jar"]

