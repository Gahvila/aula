plugins {
    java
    `maven-publish`
    id("com.gradleup.shadow") version "9.4.1"
}

group = "net.gahvila"
version = findProperty("version")!!
description = "Aula"
java.sourceCompatibility = JavaVersion.VERSION_25

java {
    toolchain.languageVersion = JavaLanguageVersion.of(25)
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
    compileOnly("net.gahvila:gahvilacore:3.0-SNAPSHOT")
    compileOnly("de.hexaoxi:carbonchat-api:3.0.0-beta.27")
    implementation("net.gahvila:inventoryframework:0.11.2-SNAPSHOT")
    implementation("com.github.simplix-softworks:simplixstorage:3.2.7")
    compileOnly("io.papermc.paper:paper-api:26.1.2.build.74-stable")
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
        relocate("de.leonhard.storage", "net.gahvila.aula.shaded.storage")
        relocate ("net.gahvila.inventoryframework", "net.gahvila.aula.shaded.inventoryframework")
    }
    processResources {
        expand(project.properties)
    }
}
