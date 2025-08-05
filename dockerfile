FROM openjdk:17-jdk-alpine
COPY examen/target/examen-0.0.1-SNAPSHOT.war examen.war
ENTRYPOINT ["java", "-jar", "examen.war"]