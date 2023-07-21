plugins {
    `kotlin-dsl`
}

repositories {
    mavenCentral()
    gradlePluginPortal()
}

dependencies {
    api(libs.kotlinGradle)
    api(libs.serializationGradle)
    api(libs.sshGradle)
    api(libs.shadowGradle)
}
