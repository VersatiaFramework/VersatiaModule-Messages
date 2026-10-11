rootProject.name = "VersatiaModule-Messages"

pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositories {
        // The Text module API is consumed from the local Maven repository (`./gradlew publishToMavenLocal` in its checkout)
        mavenLocal()
        mavenCentral()
        maven("https://repo.papermc.io/repository/maven-public/")
    }
}
