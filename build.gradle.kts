plugins {
    kotlin("jvm") version "1.9.0"
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

group = "com.nixietab"
version = "1.0.0"

repositories {
    mavenCentral()
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    implementation(kotlin("stdlib"))
    compileOnly("io.papermc.paper:paper-api:1.20.2-R0.1-SNAPSHOT")
}

tasks {
    shadowJar {
        archiveBaseName.set("TarpitPlugin")
        archiveClassifier.set("")
        archiveVersion.set("")
    }
    
    processResources {
        filesMatching("plugin.yml") {
            expand(
                "version" to project.version,
                "main" to "${project.group}.tarpitplugin.TarpitPlugin"
            )
        }
    }
    
    compileKotlin {
        kotlinOptions.jvmTarget = "17"
    }
}