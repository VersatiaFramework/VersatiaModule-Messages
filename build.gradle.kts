import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    kotlin("jvm") version "2.4.20"
    `java-library`
    `maven-publish`
}

group = "com.github.marcoral.versatia"
version = "0.0.1"

// Only Adventure (part of the Paper API) is needed; plugins have VersatiaAPI and Paper on their classpath already
dependencies {
    compileOnlyApi("io.papermc.paper:paper-api:1.21.4-R0.1-SNAPSHOT")
    api(kotlin("stdlib"))
}

// Paper 1.21.4 requires Java 21
java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
    withSourcesJar()
}

kotlin {
    compilerOptions {
        jvmTarget = JvmTarget.JVM_21
    }
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            artifactId = "VersatiaModule-Messages"
            from(components["java"])
        }
    }
}
