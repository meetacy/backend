plugins {
    id("backend-convention")
    id("serialization-convention")
}

dependencies {
    api(projects.feature.auth.types)

    implementation(libs.serializationJson)
}
