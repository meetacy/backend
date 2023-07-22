plugins {
    id("backend-convention")
    id(Deps.Plugins.Serialization.Id)
}

dependencies {
    api(projects.feature.auth.types)

    implementation(libs.serializationJson)
}
