plugins {
    id(Deps.Plugins.Configuration.Kotlin.Jvm)
    id(Deps.Plugins.Serialization.Id)
}

dependencies {
    api(project(Deps.Projects.TypesSerialization))

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
    implementation(Deps.Libs.Kotlinx.Serialization)
    implementation(Deps.Libs.Slf4j.Simple)
    implementation(Deps.Libs.Ktor.Server.RequestValidation)
    implementation(Deps.Libs.Ktor.Server.ServerStatusPage)
}
