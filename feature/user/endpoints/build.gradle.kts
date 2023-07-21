plugins {
    id("backend-convention")
    id(Deps.Plugins.Serialization.Id)
}

dependencies {
    api(project(Deps.Projects.User.Types))
    api(project(Deps.Projects.Files.Types))
    api(project(Deps.Projects.KtorExtensions))

    implementation(Deps.Libs.Ktor.Server.Core)
    implementation(Deps.Libs.Kotlinx.Serialization)
}
