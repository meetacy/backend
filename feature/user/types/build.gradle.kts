plugins {
    id("backend-convention")
    id("serialization-convention")
}

dependencies {
    api(projects.feature.auth.types)
    api(projects.core.types)

    implementation(libs.serializationGradle)
}
