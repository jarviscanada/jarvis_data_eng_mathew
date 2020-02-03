FROM maven:3.6-jdk-8-slim AS build

# Jarvis Trading App Dockerfile. Build package
COPY src /build/src
COPY pom.xml /build/
RUN mvn -f /build/pom.xml clean package -DskipTests

# Run package
FROM openjdk:8-alpine
COPY --from=build /build/target/trading-1.0-SNAPSHOT.jar /usr/local/app/trading/lib/trading_app.jar
ENTRYPOINT ["java", "-jar", "/usr/local/app/trading/lib/trading_app.jar"]
