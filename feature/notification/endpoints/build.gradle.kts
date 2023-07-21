plugins {
    id("backend-convention")
    id(Deps.Plugins.Serialization.Id)
}

dependencies {
    api(project(Deps.Projects.Notification.Types))
    api(project(Deps.Projects.KtorExtensions))

    api(project(Deps.Projects.User.Endpoints))
    api(project(Deps.Projects.Meetings.Endpoints))

    implementation(Deps.Libs.Ktor.Server.Core)
    implementation(Deps.Libs.Kotlinx.Serialization)
}
