FROM maven:3.9.4-eclipse-temurin-21 AS build

WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline

COPY src ./src
COPY uploads ./uploads

RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app

COPY --from=build /app/target/ECommerce-0.0.1-SNAPSHOT.jar ./ECommerce.jar
COPY --from=build /app/uploads ./uploads

RUN apk add --no-cache curl
RUN mkdir -p /app/logs

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=docker", "ECommerce.jar"]