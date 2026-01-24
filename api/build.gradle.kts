plugins {
    kotlin("jvm") version "2.3.0"
    kotlin("plugin.spring") version "2.3.0"
    id("org.springframework.boot") version "4.0.2"
    id("io.spring.dependency-management") version "1.1.7"
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
    mainClass.set("org.example.DemoApplication")
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.2")

    implementation("org.springframework.boot:spring-boot-starter:4.0.2")
    implementation("org.springframework.boot:spring-boot-starter-web:4.0.2")
    implementation("org.jetbrains.kotlin:kotlin-reflect:2.3.0")

    implementation(project(":domain"))
    implementation(project(":database"))

    testImplementation("org.springframework.boot:spring-boot-starter-test:4.0.2")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5:2.3.0")
    testImplementation(kotlin("test"))

    testRuntimeOnly("org.junit.platform:junit-platform-launcher:6.0.2")
}

tasks.test {
    useJUnitPlatform()
}
