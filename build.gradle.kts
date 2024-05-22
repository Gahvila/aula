plugins {
    java
    `maven-publish`
    id("io.papermc.paperweight.userdev") version "1.7.1"
    id("io.github.goooler.shadow") version "8.1.7"
}
java {
    toolchain.languageVersion = JavaLanguageVersion.of(21)
}
repositories {
    mavenLocal()
    mavenCentral()
    gradlePluginPortal()
    maven(url = "https://s01.oss.sonatype.org/content/repositories/snapshots/") {
        name = "sonatype-oss-snapshots"
    }
    maven {
        url = uri("https://repo.papermc.io/repository/maven-public/")
    }
    maven {
        url = uri("https://repo.codemc.io/repository/maven-releases/")
    }
    maven {
        url = uri("https://jitpack.io")
    }
    maven {
        url = uri("https://maven.enginehub.org/repo/")
    }
    maven {
        url = uri("https://repo.fancyplugins.de/releases")
    }
}

dependencies {
    compileOnly ("com.sk89q.worldguard:worldguard-bukkit:7.0.10")
    compileOnly ("com.github.koca2000:NoteBlockAPI:1.6.2")
    implementation ("com.github.DaJokni:simplixstorage:-SNAPSHOT")
    implementation ("com.github.stefvanschie.inventoryframework:IF:0.10.14")
    paperweight.paperDevBundle("1.20.6-R0.1-SNAPSHOT")

    //commandapi
    compileOnly("dev.jorel:commandapi-annotations:9.4.1")
    implementation("dev.jorel:commandapi-bukkit-shade-mojang-mapped:9.4.1")
    annotationProcessor("dev.jorel:commandapi-annotations:9.4.1")
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

paperweight.reobfArtifactConfiguration = io.papermc.paperweight.userdev.ReobfArtifactConfiguration.MOJANG_PRODUCTION

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
            include(dependency("dev.jorel:commandapi-bukkit-shade-mojang-mapped:9.4.1"))
            include(dependency("com.github.DaJokni:simplixstorage:-SNAPSHOT"))
            include(dependency("com.github.stefvanschie.inventoryframework:IF:0.10.14"))
        }
        relocate("dev.jorel.commandapi", "net.gahvila.aula.shaded.commandapi")
        relocate("de.leonhard.storage", "net.gahvila.survival.shaded.storage")
        relocate ("com.github.stefvanschie.inventoryframework", "net.gahvila.aula.shaded.inventoryframework")
    }
}