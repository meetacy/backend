plugins {
    id("backend-convention")
    id(Deps.Plugins.Serialization.Id)
}

dependencies {
    api(project(Deps.Projects.Invitation.Types))
    api(project(Deps.Projects.KtorExtensions))
    api(project(Deps.Projects.User.Types))
    api(project(Deps.Projects.Meetings.Endpoints))

    implementation(Deps.Libs.Ktor.Server.Core)
    implementation(Deps.Libs.Kotlinx.Serialization)
}
