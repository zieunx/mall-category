FROM openjdk:17.0.1-jdk-slim

ARG JAR_FILE=build/libs/*.jar
ARG DOCKER_FILE=app/mall-category-0.0.1.jar

COPY ${JAR_FILE} ${DOCKER_FILE}
ENTRYPOINT ["java", "-jar", "/app/mall-category-0.0.1.jar"]