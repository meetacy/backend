plugins {
    id("backend-convention")
    id(Deps.Plugins.Serialization.Id)
}

dependencies {
    api(project(Deps.Projects.DateTime))

    implementation(Deps.Libs.Kotlinx.Serialization)
}
