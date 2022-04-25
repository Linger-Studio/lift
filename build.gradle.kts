import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.6.20"
    `java-library`
    `maven-publish`
}

group = "com.github.linger-studio"
version = "1.0.0-rc05"

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.bitcoinj:bitcoinj-core:0.16.1")
    testImplementation(kotlin("test"))
}

afterEvaluate {
    publishing {
        publications {
            val mavenJava by creating(MavenPublication::class) {
                from(components["java"])
                groupId = "com.github.linger-studio"
                artifactId = "lift"
                version = "1.0.0-rc05"
            }
        }
    }
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "11"
}