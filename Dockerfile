FROM openjdk:21-jdk-slim

ARG JAR_FILE=artifacts/kilo-health-admin.jar

COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java","-jar","/app.jar"]
