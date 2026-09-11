import org.springframework.boot.gradle.tasks.bundling.BootJar


tasks.named<BootJar>("bootJar") {
    archiveFileName.set("api-gateway.jar")
}

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
        "org.springframework.cloud:" + "spring-cloud-starter-gateway-server-webflux"
    )

    implementation(
        "org.springframework.boot:spring-boot-starter-actuator"
    )

    testImplementation(
        "org.springframework.boot:spring-boot-starter-test"
    )
    implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client")
    implementation("org.springframework.cloud:spring-cloud-starter-loadbalancer")
}