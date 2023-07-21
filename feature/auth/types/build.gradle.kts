plugins {
    id("backend-convention")
    id(Deps.Plugins.Serialization.Id)
}

dependencies {
    api(project(Deps.Projects.Constants))

    implementation(Deps.Libs.Kotlinx.Serialization)
}
