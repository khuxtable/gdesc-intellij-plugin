plugins {
    id("java")
    id("org.jetbrains.intellij.platform") version "2.10.2"
    id("antlr")
}

group = "org.kathrynhuxtable.gdesc"
version = "1.0-SNAPSHOT"

// Include the generated files in the source set
sourceSets {
    main {
        java {
            srcDirs("build/generated-src/antlr/main")
        }
    }
}

repositories {
    mavenCentral()
    intellijPlatform {
        defaultRepositories()
    }
}

// Read more: https://plugins.jetbrains.com/docs/intellij/tools-intellij-platform-gradle-plugin.html
dependencies {
    antlr("org.antlr:antlr4:4.13.1")
    implementation("org.antlr:antlr4-runtime:4.13.1")
    implementation("org.antlr:antlr4-intellij-adaptor:0.1")
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
    generateGrammarSource {
        maxHeapSize = "64m"

        packageName = "org.kathrynhuxtable.gdesc.gdescplugin.parser"
        outputDirectory = file("build/generated-src/antlr/main")
        arguments = arguments + listOf("-Xexact-output-dir")
    }

    compileJava {
        dependsOn(generateGrammarSource)
    }
}
