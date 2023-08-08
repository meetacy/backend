plugins {
    `kotlin-dsl`
}

repositories {
    mavenCentral()
    gradlePluginPortal()
}

dependencies {
    implementation(projects.scripts.featureGen)
    api(libs.gradle.kotlin)
    api(libs.gradle.kotlinx.serialization)
    api(libs.gradle.ssh)
    api(libs.gradle.shadow)
}
