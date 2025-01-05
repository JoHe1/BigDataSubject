# Usa una imagen base de Java
FROM openjdk:17-jdk-slim

# Directorio de trabajo dentro del contenedor
WORKDIR /app

# Copia el JAR generado por Maven dentro del contenedor
COPY target/Individual_Task_4-1.0-fat.jar app.jar

# Comando para ejecutar la aplicación
CMD ["java", "-jar", "app.jar"]
