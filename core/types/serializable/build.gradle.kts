plugins {
    id("backend-convention")
    kotlin("plugin.serialization")
}

dependencies {
    api(projects.core.constants)
    implementation(libs.kotlinx.serialization.json)
}
