plugins {
    id(Deps.Plugins.Configuration.Kotlin.Jvm)
    id(Deps.Plugins.Serialization.Id)
}

dependencies {
    api(project(Deps.Projects.Database))
    api(project(Deps.Projects.Usecase))
    api(project(Deps.Projects.Endpoints))
    implementation(Deps.Libs.Kotlinx.Serialization)
}
