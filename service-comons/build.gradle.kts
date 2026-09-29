plugins {
    id("java")
}

group = "br.com.d2s.ecommerce.commons"
version = "0.0.1-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {

}

tasks.test {
    useJUnitPlatform()
}