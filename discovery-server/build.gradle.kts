plugins {
    java
    id("org.springframework.boot")
}

group = "br.com.d2s.ecommerce.discoveryserver"
version = "0.0.1-SNAPSHOT"

repositories {
    mavenCentral()
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