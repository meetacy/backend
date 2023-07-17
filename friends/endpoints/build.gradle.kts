plugins {
    id(Deps.Plugins.Configuration.Kotlin.Jvm)
    id(Deps.Plugins.Serialization.Id)
}

dependencies {
    api(project(Deps.Projects.KtorExtensions))
    api(project(Deps.Projects.User.Endpoints))
    api(project(Deps.Projects.Paging.Types))

    implementation(Deps.Libs.Ktor.Server.Core)
    implementation(Deps.Libs.Kotlinx.Serialization)
}
