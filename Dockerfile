FROM eclipse-temurin:17

LABEL mentainer="fscsilva0@gmail.com"

WORKDIR /app

COPY target/calculator-backend-0.0.1-SNAPSHOT.jar /app/calculator-backend.jar

ENTRYPOINT ["java", "-jar", "calculator-backend.jar"]