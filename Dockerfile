FROM eclipse-temurin:25.0.2_10-jdk

WORKDIR /app

COPY target/*.jar app.jar

RUN mkdir -p /data
COPY src/main/resources/notes.json /data/notes.json
COPY src/main/resources/users.json /data/users.json

ENV USER_NAME=Docker_Amr_Hesham
ENV ID=Docker_55_0839

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]