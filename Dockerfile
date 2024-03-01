FROM gradle:8.6.0-jdk11-focal

COPY . .

EXPOSE 8080

RUN gradle build

ENTRYPOINT ["java", "-jar", "build\libs\homebanking-0.0.1-SNAPSHOT.jar"]