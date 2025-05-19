
FROM openjdk:21


COPY target/bankcards-1.0.jar /app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
