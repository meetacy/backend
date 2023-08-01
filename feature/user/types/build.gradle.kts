plugins {
    id("backend-convention")
    id("serialization-convention")
}

dependencies {
    api(projects.core.types)
    api(projects.core.types)

    implementation(libs.serializationGradle)
}
