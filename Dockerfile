FROM maven:3.8.5-openjdk-17 AS builder
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk
WORKDIR /app
COPY --from=builder /app/target/sharedCardServer-0.0.1-SNAPSHOT.jar .
EXPOSE 8081
CMD ["java", "-jar", "sharedCardServer-0.0.1-SNAPSHOT.jar"]