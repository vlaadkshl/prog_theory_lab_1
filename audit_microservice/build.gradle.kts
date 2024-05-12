plugins {
    id("java")
    id("org.springframework.boot") version "3.2.5"
    id("io.spring.dependency-management") version "1.1.4"
}

group = "ua.nure.progtheory"
version = "unspecified"

java {
    sourceCompatibility = JavaVersion.VERSION_21
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.projectlombok:lombok")
    implementation("org.springframework.kafka:spring-kafka")
    implementation("org.liquibase:liquibase-core")
    testImplementation("org.springframework.kafka:spring-kafka-test")
    runtimeOnly("org.postgresql:postgresql")
}

tasks.test {
    useJUnitPlatform()
}