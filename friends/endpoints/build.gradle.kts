plugins {
    id(Deps.Plugins.Configuration.Kotlin.Jvm)
    id(Deps.Plugins.Serialization.Id)
}

dependencies {
    api(project(Deps.Projects.Types))
    api(project(Deps.Projects.TypesSerialization))
    api(project(Deps.Projects.KtorExtensions))
    api(project(Deps.Projects.Endpoints))
    api(project(Deps.Projects.User.Endpoints))

    implementation(Deps.Libs.Ktor.Server.Core)
    implementation(Deps.Libs.Kotlinx.Serialization)
}
