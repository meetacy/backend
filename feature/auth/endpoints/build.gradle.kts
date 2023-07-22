plugins {
    id("backend-convention")
    id("serialization-convention")
}

dependencies {
    api(projects.feature.auth.types)
    api(projects.libs.ktorExtensions)

    implementation(libs.ktorServer.core)
    implementation(libs.serializationGradle)
}
