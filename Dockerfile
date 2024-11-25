FROM openjdk
COPY . .
EXPOSE 8081
CMD ["java", "-jar", "target/sharedCardServer-0.0.1-SNAPSHOT.jar"] 

