plugins {
    id("backend-convention")
    id("serialization-convention")
}

dependencies {
    api(project(Deps.Projects.Invitation.Endpoints))
    api(project(Deps.Projects.Notification.Endpoints))
    api(project(Deps.Projects.Friends.Endpoints))
    api(project(Deps.Projects.Updates.Endpoints))
    api(project(Deps.Projects.Files.Endpoints))
    api(project(Deps.Projects.Meetings.Endpoints))
    api(project(Deps.Projects.User.Endpoints))
    api(project(Deps.Projects.Auth.Endpoints))
    api(project(Deps.Projects.KtorExtensions))

    api(Deps.Libs.Ktor.Server.Core)
    api(Deps.Libs.Ktor.Server.Cio)

    implementation(Deps.Libs.Ktor.Server.WebSockets)
    implementation(Deps.Libs.Ktor.Server.RSocket)
    implementation(Deps.Libs.Ktor.Server.Swagger)
    implementation(Deps.Libs.Ktor.Server.PartialContent)
    implementation(Deps.Libs.Ktor.Server.Cors)
    implementation(Deps.Libs.Ktor.Server.AutoHead)
    implementation(Deps.Libs.Ktor.Server.SerializationJson)
    implementation(Deps.Libs.Ktor.Server.ContentNegotiation)
    implementation(Deps.Libs.Ktor.Server.StatusPages)
    implementation(libs.serializationJson)
    implementation(Deps.Libs.Slf4j.Simple)
    implementation(Deps.Libs.Ktor.Server.RequestValidation)
    implementation(Deps.Libs.Ktor.Server.ServerStatusPage)
}
