
# === BUILD ===
FROM maven:3.9-eclipse-temurin-21-alpine AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline   # cache it

COPY src ./src
RUN mvn package -DskipTests


# === RUN (lean and mean) ===
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Security
# RUN apk update && apk upgrade
RUN addgroup -S api-group && adduser -S api-runner -G api-group
USER api-runner

COPY --from=build --chown=api-runner:api-group /app/target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
