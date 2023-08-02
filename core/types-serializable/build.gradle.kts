plugins {
    id("backend-convention")
    kotlin("plugin.serialization")
}

dependencies {
    api(projects.core.constants)
    api(projects.core.types)
    implementation(libs.kotlinx.serialization.json)
}
