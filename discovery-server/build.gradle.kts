import org.springframework.boot.gradle.tasks.bundling.BootJar


tasks.named<BootJar>("bootJar") {
    archiveFileName.set("discovery-server.jar")
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
    implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-server")
}

tasks.test {
    useJUnitPlatform()
}
