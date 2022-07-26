FROM maven:3.8.6-eclipse-temurin-17-alpine

WORKDIR /application

COPY src ./src
COPY pom.xml ./pom.xml
COPY lombok.config ./lombok.config

RUN mvn clean package

WORKDIR /

CMD java -jar /application/target/*.jar
