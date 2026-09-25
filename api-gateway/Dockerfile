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
COPY customer-service/build.gradle.kts customer-service/build.gradle.kts

RUN chmod +x gradlew
#RUN ./gradlew :api-gateway:dependencies --no-daemon

COPY ./api-gateway ./api-gateway

RUN ./gradlew :api-gateway:bootJar \
    --no-daemon \
    --stacktrace

#CMD ["./gradlew", ":api-gateway:bootRun", "-PskipDownload=true", "--no-daemon"]

FROM eclipse-temurin:25-jre

WORKDIR /app

COPY --from=builder \
    /workspace/api-gateway/build/libs/api-gateway.jar \
    api-gateway.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "api-gateway.jar"]