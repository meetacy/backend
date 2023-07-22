plugins {
    id("backend-convention")
    id("serialization-convention")
}

dependencies {
    api(projects.feature.auth.types)
    api(projects.location)
    api(projects.amount)
    api(projects.optional)

    implementation(libs.serializationGradle)
}
