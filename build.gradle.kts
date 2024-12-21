import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    kotlin("jvm") version "2.1.0"
    id("com.gradleup.shadow") version "9.0.0-beta4"
}

group = "Heyblock0712"
version = "0.0.3"

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/") {
        name = "papermc-repo"
    }
    maven("https://oss.sonatype.org/content/groups/public/") {
        name = "sonatype"
    }
    maven("https://repo.codemc.io/repository/maven-public/") {
        name = "CodeMC"
    }
    maven("https://jitpack.io") {
    }
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.21.3-R0.1-SNAPSHOT")
    compileOnly("com.github.HN-Cord:HNLib:cd6c4d48dc")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("de.tr7zw:item-nbt-api:2.14.1")
}

val targetJavaVersion = 21
kotlin {
    jvmToolchain(targetJavaVersion)
}

tasks.build {
    dependsOn("shadowJar")
}

tasks.processResources {
    val props = mapOf("version" to version)
    inputs.properties(props)
    filteringCharset = "UTF-8"
    filesMatching("paper-plugin.yml") {
        expand(props)
    }
}

tasks {
    withType<ShadowJar> {
        // 重新命名 NBT-API 库
        relocate("de.tr7zw.changeme.nbtapi", "hn.blacknight0981.nbtapi")
    }

    shadowJar {
        archiveClassifier.set("")
    }
}
