plugins {
    java
    `maven-publish`
    id("io.github.goooler.shadow") version "8.1.7"
}

group = "net.gahvila"
version = findProperty("version")!!
description = "Aula"
java.sourceCompatibility = JavaVersion.VERSION_21

java {
    toolchain.languageVersion = JavaLanguageVersion.of(21)
}
repositories {
    mavenLocal()
    mavenCentral()
    gradlePluginPortal()
    maven{ url = uri("https://s01.oss.sonatype.org/content/repositories/snapshots/") }
    maven { url = uri("https://repo.papermc.io/repository/maven-public/") }
    maven { url = uri("https://repo.codemc.io/repository/maven-releases/") }
    maven { url = uri("https://jitpack.io") }
}

dependencies {
    compileOnly("net.gahvila:GahvilaCore:2.0")
    compileOnly("de.hexaoxi:carbonchat-api:3.0.0-beta.27")
    implementation("com.github.simplix-softworks:simplixstorage:3.2.7")
    implementation ("com.github.stefvanschie.inventoryframework:IF-Paper:0.11.1-SNAPSHOT")
    compileOnly("io.papermc.paper:paper-api:1.21.4-R0.1-SNAPSHOT")

    //commandapi
    compileOnly("dev.jorel:commandapi-annotations:9.7.0")
    implementation("dev.jorel:commandapi-bukkit-shade-mojang-mapped:9.7.0")
    annotationProcessor("dev.jorel:commandapi-annotations:9.7.0")
}

publishing {
    publications.create<MavenPublication>("maven") {
        from(components["java"])
    }
}

tasks {
    withType<JavaCompile> {
        options.encoding = "UTF-8"
    }
    build {
        dependsOn(shadowJar)
    }
    assemble {
        dependsOn(shadowJar)
    }
    shadowJar {
        archiveFileName.set("${rootProject.name}-${version}.jar")
        relocate("dev.jorel.commandapi", "net.gahvila.aula.shaded.commandapi")
        relocate("de.leonhard.storage", "net.gahvila.aula.shaded.storage")
        relocate ("com.github.stefvanschie.inventoryframework", "net.gahvila.aula.shaded.inventoryframework")
    }
    processResources {
        expand(project.properties)
    }
}