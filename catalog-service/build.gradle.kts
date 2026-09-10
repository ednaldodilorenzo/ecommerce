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

//    implementation(
//        "org.springframework.boot:spring-boot-starter-data-jpa"
//    )

    implementation(
        "org.springframework.boot:spring-boot-starter-validation"
    )

    implementation(
        "org.springframework.boot:spring-boot-starter-actuator"
    )

    implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client")

    runtimeOnly("org.postgresql:postgresql")

    testImplementation(
        "org.springframework.boot:spring-boot-starter-test"
    )

}