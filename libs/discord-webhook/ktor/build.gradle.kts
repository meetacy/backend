plugins {
    id(Deps.Plugins.Configuration.Kotlin.Jvm)
}

dependencies {
    implementation(project(Deps.Projects.DiscordWebhook))
    implementation(Deps.Libs.Ktor.Client.Core)
    implementation(Deps.Libs.Ktor.Client.ContentNegotiation)
    implementation(Deps.Libs.Ktor.Client.SerializationJson)
    implementation(Deps.Libs.Ktor.Client.Cio)
}
