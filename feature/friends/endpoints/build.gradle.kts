plugins {
    id("backend-convention")
    id(Deps.Plugins.Serialization.Id)
}

dependencies {
    api(projects.core.typesSerializable.integration)
    api(projects.libs.ktorExtensions)
    api(projects.feature.user.endpoints)
    api(projects.libs.paging.types)

    implementation(libs.ktorServer.core)
    implementation(libs.serializationJson)
}
