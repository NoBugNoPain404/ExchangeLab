plugins {
    id("java-library")
    id("org.springframework.boot")
}

dependencies {
    api("jakarta.persistence:jakarta.persistence-api:3.2.0")
    implementation("com.fasterxml.jackson.core:jackson-annotations")
    implementation("org.springframework.boot:spring-boot-starter-data-redis")
    implementation("org.springframework.boot:spring-boot-starter-web")
}