FROM maven:3.8.3-openjdk-17 AS build
COPY . .
RUN mvn clean package -DskipTests
FROM openjdk:17-ea-28-jdk-slim
COPY --from=build /target/emailsApp-0.0.1-SNAPSHOT.jar back.jar
EXPOSE 8085
ENTRYPOINT [ "java","-jar","back.jar" ]
