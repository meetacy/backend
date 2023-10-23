plugins {
    id("backend-convention")
}

dependencies {
    implementation(projects.libs.discordWebhook)
    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.cio)
    implementation(libs.ktor.client.contentNegotiation)
    implementation(libs.ktor.client.serializationJson)
}
