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
    maven("https://s01.oss.sonatype.org/content/repositories/snapshots/")
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://repo.codemc.io/repository/maven-releases/")
    maven("https://repo.gahvila.net/snapshots/")
    maven("https://jitpack.io")

}

dependencies {
    implementation("net.gahvila:gahvilacore:2.3-SNAPSHOT")
    compileOnly("de.hexaoxi:carbonchat-api:3.0.0-beta.27")
    implementation("net.gahvila:inventoryframework:0.11.2-SNAPSHOT")
    implementation("com.github.simplix-softworks:simplixstorage:3.2.7")
    compileOnly("io.papermc.paper:paper-api:1.21.7-R0.1-SNAPSHOT")

    //commandapi
    compileOnly("dev.jorel:commandapi-annotations:10.1.1")
    implementation("dev.jorel:commandapi-bukkit-shade-mojang-mapped:10.1.1")
    annotationProcessor("dev.jorel:commandapi-annotations:10.1.1")
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
        relocate ("net.gahvila.inventoryframework", "net.gahvila.aula.shaded.inventoryframework")
    }
    processResources {
        expand(project.properties)
    }
}