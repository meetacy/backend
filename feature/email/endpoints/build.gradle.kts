plugins {
    id("backend-convention")
    id(Deps.Plugins.Serialization.Id)
}

dependencies {
    api(projects.libs.ktorExtensions)

    implementation(libs.ktorServer.core)
    implementation(libs.serializationJson)
}
