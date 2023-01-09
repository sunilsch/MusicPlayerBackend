FROM openjdk:8-jdk-alpine
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} MusicPlayerBackendApplication-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/MusicPlayerBackendApplication-0.0.1-SNAPSHOT.jar"]