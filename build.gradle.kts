import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    java
    idea
    `maven-publish`
    kotlin("jvm") version "1.3.50"
    kotlin("plugin.serialization") version "1.3.50"
    id("org.jetbrains.intellij") version "0.4.10"
}

group = "dev.feedforward"
version = "0.0.1"

repositories {
    mavenCentral()
    jcenter()
}

dependencies {
    compile("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.3.50")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

intellij {
    version = "IC-2019.2"
}

sourceSets {
    main {
        java {
            srcDir("src/main/gen")
        }
    }
}

idea {
    module {
        generatedSourceDirs.add(file("src/main/gen"))
    }
}

val sourcesJar by tasks.registering(Jar::class) {
    classifier = "sources"
    from(sourceSets.main.get().allSource)
}

publishing {
    publications {
        register("mavenPublication", MavenPublication::class) {
            from(components["java"])
            artifact(sourcesJar.get())
        }
    }
}
