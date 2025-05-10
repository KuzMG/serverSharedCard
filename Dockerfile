FROM maven:3.8.6-openjdk-11 AS builder
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests


FROM openjdk:11-jre-slim
WORKDIR /app
COPY --from=builder /app/target/sharedCardServer-0.0.1-SNAPSHOT.jar .
EXPOSE 8081
CMD ["java", "-jar", "sharedCardServer-0.0.1-SNAPSHOT.jar"]