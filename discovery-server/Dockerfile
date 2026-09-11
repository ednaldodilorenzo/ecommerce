FROM eclipse-temurin:25-jdk-jammy AS builder

WORKDIR /workspace

COPY gradlew .
COPY gradle gradle
COPY settings.gradle.kts .
COPY build.gradle.kts .
COPY gradle.properties .

COPY api-gateway/build.gradle.kts api-gateway/build.gradle.kts
COPY catalog-service/build.gradle.kts catalog-service/build.gradle.kts
COPY discovery-server/build.gradle.kts discovery-server/build.gradle.kts

RUN chmod +x gradlew

COPY ./discovery-server ./discovery-server

RUN ./gradlew :discovery-server:bootJar \
    --no-daemon \
    --stacktrace

#CMD ["./gradlew", ":api-gateway:bootRun", "-PskipDownload=true", "--no-daemon"]

FROM eclipse-temurin:25-jre

WORKDIR /app

COPY --from=builder \
    /workspace/discovery-server/build/libs/discovery-server.jar \
    discovery-server.jar

EXPOSE 8761

ENTRYPOINT ["java", "-jar", "discovery-server.jar"]