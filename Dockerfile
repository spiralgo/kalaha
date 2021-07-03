FROM adoptopenjdk/openjdk11:alpine-slim
MAINTAINER hunaltay
EXPOSE 8080
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} kalaha.jar
ENTRYPOINT ["java", "-jar", "kalaha.jar"]