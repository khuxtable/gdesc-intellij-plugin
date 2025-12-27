plugins {
    id("java")
    id("org.jetbrains.intellij.platform") version "2.10.2"
//    id("antlr")
}

group = "org.kathrynhuxtable.gdesc"
version = "1.0-SNAPSHOT"

repositories {
    mavenLocal()
    mavenCentral()
    intellijPlatform {
        defaultRepositories()
    }
}

dependencies {
//    antlr("org.antlr:antlr4:4.13.1")
    implementation("org.antlr:antlr4-runtime:4.13.1")
    implementation("org.antlr:antlr4-intellij-adaptor:0.1")
    implementation("org.kathrynhuxtable.gdesc:gdesc-parser:3.0.0-SNAPSHOT")
    implementation("org.projectlombok:lombok:1.18.42")
    intellijPlatform {
        intellijIdea("2025.2.4")
        testFramework(org.jetbrains.intellij.platform.gradle.TestFrameworkType.Platform)

        // Add plugin dependencies for compilation here:
        bundledPlugin("com.intellij.java")
    }
}

intellijPlatform {
    pluginConfiguration {
        ideaVersion {
            sinceBuild = "252.25557"
        }

        changeNotes = """
            Initial version
        """.trimIndent()
    }
}

tasks {
    // Set the JVM compatibility versions
    withType<JavaCompile> {
        sourceCompatibility = "21"
        targetCompatibility = "21"
    }
}
