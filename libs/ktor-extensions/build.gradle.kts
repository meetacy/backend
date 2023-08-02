plugins {
    id("backend-convention")
    id("serialization-convention")
}

dependencies {
    api(libs.ktor.server.core)
    api(libs.ktor.server.cio)
    api(libs.ktor.server.rsocket)

    implementation(libs.ktor.server.websockets)
    implementation(libs.ktor.server.swagger)
    implementation(libs.ktor.server.partialContent)
    implementation(libs.ktor.server.cors)
    implementation(libs.ktor.server.autoHead)
    implementation(libs.ktor.server.serializationJson)
    implementation(libs.ktor.server.contentNegotiation)
    implementation(libs.ktor.server.statusPages)
    implementation(libs.gradle.kotlinx.serialization)
    implementation(libs.ktor.server.requestValidation)
    implementation(libs.ktor.server.serverStatusPage)
    implementation(libs.slf4j.simple)
}
