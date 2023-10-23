plugins {
    id("backend-convention")
}

dependencies {
//    implementation(project(Deps.Projects.DiscordWebhook))
//    implementation(Deps.Libs.Ktor.Client.Core)
//    implementation(Deps.Libs.Ktor.Client.ContentNegotiation)
//    implementation(Deps.Libs.Ktor.Client.SerializationJson)
//    implementation(Deps.Libs.Ktor.Client.Cio)
    api(projects.libs.discordWebhook)
    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.cio)
    implementation(libs.ktor.client.contentNegotiation)
    implementation(libs.ktor.client.serializationJson)
}