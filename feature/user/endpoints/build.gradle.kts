plugins {
    id("backend-convention")
    id("serialization-convention")
}

dependencies {
    api(projects.feature.user.types)
    api(projects.feature.files.types)
    api(projects.libs.ktorExtensions)

    implementation(libs.ktorServer.core)
    implementation(libs.serializationJson)
}
