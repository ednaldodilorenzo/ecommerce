val springCloudVersion = providers.gradleProperty("springCloudVersion").get()

dependencyManagement {
    imports {
        mavenBom(
            "org.springframework.cloud:spring-cloud-dependencies:$springCloudVersion"
        )
    }
}

dependencies {
    implementation(
        "org.springframework.boot:spring-boot-starter-web"
    )

    implementation(
        "org.springframework.boot:spring-boot-starter-data-jpa"
    )

    implementation("org.liquibase:liquibase-core")

    implementation(
        "org.springframework.boot:spring-boot-starter-validation"
    )

    implementation(
        "org.springframework.boot:spring-boot-starter-actuator"
    )

    implementation(project(":service-comons"))

    implementation("org.springframework.boot:spring-boot-starter-kafka")

    implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client")

    implementation("org.springframework.boot:spring-boot-starter-restclient")

    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")

    developmentOnly("org.springframework.boot:spring-boot-devtools")

    runtimeOnly("org.postgresql:postgresql")

    testImplementation(
        "org.springframework.boot:spring-boot-starter-test"
    )

}