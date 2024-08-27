#IMAGEN BASE
FROM eclipse-temurin:17.0.12_7-jdk
#PUERTO QUE SE VA A EXPONER
EXPOSE 8080
#DIRECTORIO DE TRABAJO
WORKDIR /app
#COPIAR Y PEGAR ARCHIVOS DENTRO DEL CONTENEDOR
COPY pom.xml /app
COPY .mvn /app/.mvn
COPY mvnw /app
#DESCARGAR LAS DEPENDENCIAS
RUN ./mvnw dependency:go-offline

#COPIAR EL RESTO DE LOS ARCHIVOS
COPY src /app/src

#CONSTRUIR EL PROYECTO
RUN ./mvnw clean install -DskipTests

#EJECUTAR EL PROYECTO
ENTRYPOINT ["java","-jar","/app/target/expense-tracker-0.0.1-SNAPSHOT.jar"]