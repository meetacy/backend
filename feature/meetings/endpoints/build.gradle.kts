plugins {
    id("backend-convention")
    id(Deps.Plugins.Serialization.Id)
}

dependencies {
    api(projects.core.typesSerializable.integration)

    api(projects.feature.meetings.types)
    api(projects.feature.files.types)
    api(projects.libs.ktorExtensions)

    api(projects.feature.user.endpoints)
    api(projects.libs.paging.types)

    implementation(libs.serializationJson)
}
