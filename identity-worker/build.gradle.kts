import org.springframework.boot.gradle.tasks.bundling.BootJar

tasks.named<BootJar>("bootJar") {
    archiveFileName.set("identity-worker.jar")
}

dependencies {
    developmentOnly("org.springframework.boot:spring-boot-devtools")

    implementation(
        "org.springframework.boot:spring-boot-starter"
    )

    implementation(
        "org.springframework.boot:spring-boot-starter-restclient"
    )

    implementation("org.springframework.boot:spring-boot-starter-kafka")

    implementation("com.fasterxml.jackson.core:jackson-databind")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")
    implementation(
        "org.springframework.boot:spring-boot-starter-validation"
    )

    implementation(project(":service-comons"))

    testImplementation(
        "org.springframework.boot:spring-boot-starter-test"
    )

    testImplementation(
        "org.springframework.kafka:spring-kafka-test"
    )
}