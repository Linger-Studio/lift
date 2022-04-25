import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.6.20"
    `java-library`
    `maven-publish`
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
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
                version = "1.0.0-rc02"
            }
        }
    }
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}