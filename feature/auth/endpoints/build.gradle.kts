plugins {
    id("backend-convention")
    id("serialization-convention")
}

dependencies {
    api(projects.core.types)
    api(projects.libs.ktorExtensions)

    implementation(libs.ktorServer.core)
    implementation(libs.serializationJson)
}
