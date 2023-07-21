plugins {
    id("backend-convention")
    id(Deps.Plugins.Serialization.Id)
}

dependencies {
    api(project(Deps.Projects.Meetings.Types))
    api(project(Deps.Projects.Files.Types))
    api(project(Deps.Projects.KtorExtensions))

    api(project(Deps.Projects.User.Endpoints))
    api(project(Deps.Projects.Paging.Types))

    implementation(Deps.Libs.Ktor.Server.Core)
    implementation(Deps.Libs.Kotlinx.Serialization)
}
