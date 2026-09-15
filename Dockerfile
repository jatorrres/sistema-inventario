# Etapa 1: Compilar el proyecto con Maven y Java 17
FROM eclipse-temurin:17-jdk-alpine AS build
WORKDIR /app

# Copiamos los archivos de Maven y el código fuente
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src

# Damos permisos de ejecución al maven wrapper y compilamos omitiendo tests
RUN chmod +x mvnw
RUN ./mvnw clean package -DskipTests

# Etapa 2: Imagen final ligera para correr la aplicación
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app

# Copiamos el .jar generado desde la etapa de compilación
COPY --from=build /app/target/*.jar app.jar

# Puerto y comando de inicio
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]