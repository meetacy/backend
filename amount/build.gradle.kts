plugins {
    id("backend-convention")
    kotlin("plugin.serialization") version "1.9.0"
}

dependencies {
    api(projects.annotations)

    implementation(libs.serializationGradle)
    implementation(libs.serializationJson)
}
