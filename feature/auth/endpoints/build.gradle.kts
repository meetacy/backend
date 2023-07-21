plugins {
    id(Deps.Plugins.Configuration.Kotlin.Jvm)
    id(Deps.Plugins.Serialization.Id)
}

dependencies {
    api(project(Deps.Projects.Auth.Types))
    api(project(Deps.Projects.KtorExtensions))

    implementation(Deps.Libs.Ktor.Server.Core)
    implementation(Deps.Libs.Kotlinx.Serialization)
}
