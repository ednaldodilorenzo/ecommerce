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
    developmentOnly("org.springframework.boot:spring-boot-devtools")

    implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-server")
}

tasks.test {
    useJUnitPlatform()
}
