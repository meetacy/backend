plugins {
    id("backend-convention")
    id(Deps.Plugins.Serialization.Id)
}

dependencies {
    api(project(Deps.Projects.AmountTypes))

    implementation(Deps.Libs.Kotlinx.Serialization)
}
