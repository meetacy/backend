plugins {
    id("backend-convention")
    id("serialization-convention")
}

dependencies {
    api(projects.feature.invitations.endpoints)
    api(projects.feature.notification.endpoints)
    api(projects.feature.meetings.endpoints)
    api(projects.feature.files.endpoints)
    api(projects.feature.user.endpoints)
    api(projects.feature.auth.endpoints)
    api(projects.feature.friends.endpoints)
    api(projects.feature.updates.endpoints)
    api(projects.libs.ktorExtensions)

    api(libs.ktor.server.core)
    api(libs.ktor.server.cio)

    implementation(libs.ktor.server.websockets)
    implementation(libs.ktor.server.rsocket)
    implementation(libs.ktor.server.swagger)
    implementation(libs.ktor.server.partialContent)
    implementation(libs.ktor.server.cors)
    implementation(libs.ktor.server.autoHead)
    implementation(libs.ktor.server.serializationJson)
    implementation(libs.ktor.server.contentNegotiation)
    implementation(libs.ktor.server.statusPages)
    implementation(libs.ktor.server.requestValidation)
    implementation(libs.ktor.server.serverStatusPage)

    implementation(libs.kotlinx.serialization.json)
    implementation(libs.slf4j.simple)
}
