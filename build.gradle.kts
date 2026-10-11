import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    kotlin("jvm") version "2.4.20"
    `java-library`
    `maven-publish`
}

group = "com.github.marcoral.versatia"
version = "0.0.1"

// Messages take Text arguments, so the Text module API is exported to consumers; Adventure comes with the Paper API
dependencies {
    compileOnlyApi("io.papermc.paper:paper-api:1.21.4-R0.1-SNAPSHOT")
    api("com.github.marcoral.versatia:VersatiaModule-Text:0.0.1")
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
        jvmTarget.set(JvmTarget.JVM_21)
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
