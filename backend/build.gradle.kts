plugins {
    id("org.springframework.boot") version "3.5.0" apply false
    id("io.spring.dependency-management") version "1.1.7" apply false
    id("org.flywaydb.flyway") version "12.6.0" apply false
}

allprojects {

    group = "huynguyen.mock_binance"
    version = "1.0-SNAPSHOT"

    repositories {
        mavenCentral()
    }
}

subprojects {

    apply(plugin = "java")
    apply(plugin = "io.spring.dependency-management")

    extensions.configure<JavaPluginExtension> {
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(21))
        }
    }

    configurations.named("compileOnly") {
        extendsFrom(configurations.named("annotationProcessor").get())
    }

    dependencies {
        add("compileOnly", "org.projectlombok:lombok:1.18.38")
        add("annotationProcessor", "org.projectlombok:lombok:1.18.38")
        add("testCompileOnly", "org.projectlombok:lombok:1.18.38")
        add("testAnnotationProcessor", "org.projectlombok:lombok:1.18.38")
        add("implementation","org.mapstruct:mapstruct:1.6.3")
        add("annotationProcessor","org.mapstruct:mapstruct-processor:1.6.3")
        add("annotationProcessor","org.projectlombok:lombok-mapstruct-binding:0.2.0")
        add("testImplementation", "org.junit.jupiter:junit-jupiter:5.13.0")
        add("implementation", "org.springframework.boot:spring-boot-starter-data-redis:3.5.0")
        add("implementation", "com.fasterxml.jackson.core:jackson-annotations:2.17.1")
        add("implementation", "org.springframework.boot:spring-boot-starter-jdbc:3.5.0")
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }
}