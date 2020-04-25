import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.1.3.RELEASE"
    id("io.spring.dependency-management") version "1.0.9.RELEASE"
    id("org.jetbrains.kotlin.plugin.allopen") version "1.3.70"
    id("org.jetbrains.kotlin.plugin.noarg") version "1.3.70"
    id("com.github.node-gradle.node") version "1.5.3"
    kotlin("jvm") version "1.3.61"
    kotlin("plugin.spring") version "1.3.61"
}

allOpen {
    annotation("javax.persistence.Entity")
}

noArg {
    annotation("javax.persistence.Entity")
    invokeInitializers = true
}

group = "com.skrypnik"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_1_8

val developmentOnly by configurations.creating
configurations {
    runtimeClasspath {
        extendsFrom(developmentOnly)
    }
}

task<com.moowork.gradle.node.yarn.YarnTask>("buildFront") {
    dependsOn("yarn_install")
    args = listOf("build")
}

tasks.getByName("yarn_install").dependsOn("yarn_cache_clean")
tasks.getByName("processResources").dependsOn("buildFront")

repositories {
    mavenCentral()
}

node{
    download=true
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.session:spring-session-jdbc")
    implementation("org.hibernate:hibernate-gradle-plugin:5.4.12.Final")
    implementation("org.springframework.security.oauth.boot:spring-security-oauth2-autoconfigure:2.1.1.RELEASE")
    implementation("org.postgresql:postgresql")
    implementation("javax.xml.bind:jaxb-api")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "1.8"
    }
}
