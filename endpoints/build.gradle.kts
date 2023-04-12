plugins {
    id(Deps.Plugins.Configuration.Kotlin.Jvm)
    id(Deps.Plugins.Serialization.Id)
}

dependencies {
    api(project(Deps.Projects.TypesSerialization))

    api(Deps.Libs.Ktor.Server.Core)
    api(Deps.Libs.Ktor.Server.Cio)
    api(Deps.Libs.Ktor.Client.Core)
    api(Deps.Libs.Ktor.Client.Cio)
    api(Deps.Libs.Ktor.Server.Swagger)
    api(Deps.Libs.Ktor.Server.PartialContent)
    api(Deps.Libs.Ktor.Server.Cors)
    api(Deps.Libs.Ktor.Server.AutoHead)
    implementation(Deps.Libs.Ktor.Server.SerializationJson)
    implementation(Deps.Libs.Ktor.Server.ContentNegotiation)
    implementation(Deps.Libs.Kotlinx.Serialization)
    implementation(Deps.Libs.Slf4j.Simple)
}
