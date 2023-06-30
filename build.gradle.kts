@file:Suppress("SuspiciousCollectionReassignment")

import org.jetbrains.kotlin.gradle.dsl.KotlinCompile

group = AppInfo.PACKAGE
version = AppInfo.VERSION

allprojects {
    repositories {
        mavenLocal()
        mavenCentral()
        maven {
            url = uri("https://maven.pkg.github.com/meetacy/sdk")
            credentials {
                username = System.getenv("GITHUB_USERNAME")
                password = System.getenv("GITHUB_TOKEN")
            }
        }
        maven {
            url = uri("https://maven.pkg.github.com/meetacy/wdater")
            credentials {
                username = System.getenv("GITHUB_USERNAME")
                password = System.getenv("GITHUB_TOKEN")
            }
        }
    }

    tasks.withType<KotlinCompile<*>> {
        kotlinOptions {
            freeCompilerArgs += "-opt-in=kotlinx.serialization.ExperimentalSerializationApi"
            freeCompilerArgs += "-opt-in=kotlin.RequiresOptIn"
        }
    }
}
