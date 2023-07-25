plugins {
    id("backend-convention")
    id(Deps.Plugins.Serialization.Id)
}

dependencies {
    api(projects.feature.updates.types)
    api(projects.libs.ktorExtensions)
    api(projects.feature.notification.endpoints)

    implementation(libs.ktorServer.core)
    implementation(libs.serializationJson)
}
