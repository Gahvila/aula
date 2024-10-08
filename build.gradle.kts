plugins {
    java
    `maven-publish`
    id("io.github.goooler.shadow") version "8.1.7"
}
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
    maven { url = uri("https://repo.fancyplugins.de/releases") }
}

dependencies {
    compileOnly("net.gahvila:GahvilaCore:2.0")
    compileOnly("de.hexaoxi:carbonchat-api:3.0.0-beta.27")
    compileOnly ("com.github.koca2000:NoteBlockAPI:1.6.2")
    implementation("com.github.simplix-softworks:simplixstorage:3.2.7")
    implementation ("com.github.stefvanschie.inventoryframework:IF:0.10.17")
    compileOnly("io.papermc.paper:paper-api:1.21.1-R0.1-SNAPSHOT")

    //commandapi
    compileOnly("dev.jorel:commandapi-annotations:9.5.3")
    implementation("dev.jorel:commandapi-bukkit-shade:9.5.3")
    annotationProcessor("dev.jorel:commandapi-annotations:9.5.3")
}

group = "Aula"
version = "3.0"
description = "Aula"
java.sourceCompatibility = JavaVersion.VERSION_21

publishing {
    publications.create<MavenPublication>("maven") {
        from(components["java"])
    }
}

tasks {
    compileJava {
        options.release = 21
    }
    withType<JavaCompile> {
        options.encoding = "UTF-8"
    }
    assemble {
        dependsOn(shadowJar)
    }
    shadowJar {
        archiveFileName.set("${rootProject.name}-${version}.jar")
        dependencies {
            include(dependency("dev.jorel:commandapi-bukkit-shade:9.5.3"))
            include(dependency("com.github.simplix-softworks:simplixstorage:3.2.7"))
            include(dependency("com.github.stefvanschie.inventoryframework:IF:0.10.17"))
        }
        relocate("dev.jorel.commandapi", "net.gahvila.aula.shaded.commandapi")
        relocate("de.leonhard.storage", "net.gahvila.aula.shaded.storage")
        relocate ("com.github.stefvanschie.inventoryframework", "net.gahvila.aula.shaded.inventoryframework")
    }
}