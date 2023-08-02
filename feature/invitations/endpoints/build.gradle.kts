plugins {
    id("backend-convention")
    id(Deps.Plugins.Serialization.Id)
}

dependencies {
    api(projects.libs.ktorExtensions)
    api(projects.feature.meetings.endpoints)

    implementation(libs.serializationJson)
}
