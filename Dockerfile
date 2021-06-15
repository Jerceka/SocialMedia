# syntax=docker/dockerfile:1

FROM maven

WORKDIR /app

COPY src ./src
COPY pom.xml ./
RUN mvn clean install
