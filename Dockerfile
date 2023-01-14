FROM maven:3.8.5-openjdk-17-slim as builder
WORKDIR /src
COPY . .
RUN mvn clean install -Dmaven.test.skip

FROM openjdk:17-alpine3.14
COPY --from=builder /src/target/inside-test-0.0.1-SNAPSHOT.jar inside-test.jar
EXPOSE 80
ENTRYPOINT ["java", "-jar", "inside-test.jar"]