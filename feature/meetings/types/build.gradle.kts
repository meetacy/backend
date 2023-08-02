plugins {
    id("backend-convention")
    id(Deps.Plugins.Serialization.Id)
}

dependencies {
    api(projects.core.types)

    implementation(libs.kotlinx.serialization.json)
}
