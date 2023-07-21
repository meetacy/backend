plugins {
    id("backend-convention")
    id(Deps.Plugins.Serialization.Id)
}

dependencies {
    api(project(Deps.Projects.Auth.Types))
    api(project(Deps.Projects.LocationTypes))
    api(project(Deps.Projects.AmountTypes))
    api(project(Deps.Projects.Optional))

    implementation(Deps.Libs.Kotlinx.Serialization)
}
