FROM eclipse-temurin:25-jdk AS builder

WORKDIR /workspace

COPY . .

RUN chmod +x gradlew

RUN ./gradlew :identity-worker:bootJar \
    --no-daemon


FROM eclipse-temurin:25-jre

WORKDIR /app

COPY --from=builder \
    /workspace/identity-worker/build/libs/identity-worker.jar \
    app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]