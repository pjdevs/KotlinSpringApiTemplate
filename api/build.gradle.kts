plugins {
    kotlin("jvm") version "2.3.0"
    kotlin("plugin.spring") version "2.3.0"
    id("org.springframework.boot") version "4.0.2"
    id("io.spring.dependency-management") version "1.1.7"
    id("org.flywaydb.flyway") version "11.20.3"
    application
}

version = "0.0.1"
description = "API project using Spring Boot"

repositories {
    mavenCentral()
}

kotlin {
    jvmToolchain(25)
}

application {
    mainClass.set("org.example.demo.api.MainKt")
}

val profile = System.getenv("SPRING_PROFILES_ACTIVE") ?: "dev"

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.2")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor:1.10.2")
    implementation("org.jetbrains.kotlin:kotlin-reflect:2.3.0")

    implementation("org.springframework.boot:spring-boot-starter:4.0.2")
    implementation("org.springframework.boot:spring-boot-starter-webflux:4.0.2")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:4.0.2")
    implementation("org.springframework.boot:spring-boot-starter-jdbc:4.0.2")
    implementation("org.springframework.boot:spring-boot-starter-flyway:4.0.2")
    implementation("org.springframework.boot:spring-boot-starter-actuator:4.0.2")
    implementation("org.springframework.boot:spring-boot-starter-opentelemetry:4.0.2")

    implementation("org.springdoc:springdoc-openapi-starter-webflux-ui:3.0.1")

    implementation("tools.jackson.module:jackson-module-kotlin:3.0.4")

    implementation("org.flywaydb:flyway-core:11.20.3")
    implementation("org.flywaydb:flyway-database-postgresql:11.20.3")

    implementation("org.xerial:sqlite-jdbc:3.51.1.0")
    implementation("org.postgresql:postgresql:42.7.9")

    implementation(project(":domain"))
    implementation(project(":infra"))

    testImplementation("org.springframework.boot:spring-boot-starter-test:4.0.2")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5:2.3.0")
    testImplementation(kotlin("test"))

    testRuntimeOnly("org.junit.platform:junit-platform-launcher:6.0.2")
}

tasks.test {
    useJUnitPlatform()
}
