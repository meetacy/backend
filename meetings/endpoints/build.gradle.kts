plugins {
    id(Deps.Plugins.Configuration.Kotlin.Jvm)
    id(Deps.Plugins.Serialization.Id)
}

dependencies {
    api(project(Deps.Projects.Meetings.Types))
    api(project(Deps.Projects.Types))
    api(project(Deps.Projects.TypesSerialization))
    api(project(Deps.Projects.KtorExtensions))

    implementation(Deps.Libs.Ktor.Server.Core)
    implementation(Deps.Libs.Kotlinx.Serialization)
}
