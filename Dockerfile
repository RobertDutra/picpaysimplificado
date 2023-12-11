FROM openjdk:17

WORKDIR /app

COPY target/picpaysimplificado-0.0.1-SNAPSHOT.jar /app/picpaysimplificado.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "picpaysimplificado.jar"]