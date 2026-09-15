# Usamos una imagen ligera de Java 17
FROM eclipse-temurin:17-jdk-alpine

# Copiamos el archivo .jar que genera Maven dentro del contenedor
COPY target/*.jar app.jar

# Comando para ejecutar la aplicación cuando encienda Render
ENTRYPOINT ["java","-jar","/app.jar"]