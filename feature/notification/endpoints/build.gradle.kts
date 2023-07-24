plugins {
    id("backend-convention")
    id(Deps.Plugins.Serialization.Id)
}

dependencies {
    api(projects.feature.notification.types)
    api(projects.libs.ktorExtensions)

    api(projects.feature.user.endpoints)
    api(projects.feature.meetings.endpoints)

    implementation(libs.ktorServer.core)
    implementation(libs.serializationJson)
}
