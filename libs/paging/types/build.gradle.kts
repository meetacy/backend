plugins {
    id("backend-convention")
    id("serialization-convention")
}

dependencies {
    api(projects.core.types)

    implementation(libs.kotlinx.serialization.json)
}
