plugins {
    id("backend-convention")
    id("serialization-convention")
}

dependencies {
    implementation(libs.meetacy.di.core)

    implementation(projects.feature.auth.endpoints.integration)
    implementation(projects.feature.email.endpoints.integration)
    implementation(projects.feature.files.endpoints.integration)
    implementation(projects.feature.invitations.endpoints.integration)
    implementation(projects.feature.notifications.endpoints.integration)
    implementation(projects.feature.meetings.endpoints.integration)
    implementation(projects.feature.users.endpoints.integration)
    implementation(projects.feature.friends.endpoints.integration)
    implementation(projects.feature.updates.endpoints)

    implementation(projects.feature.auth.usecase.integration)
    implementation(projects.feature.email.usecase.integration)
    implementation(projects.feature.files.usecase.integration)
    implementation(projects.feature.files.database.integration)
    implementation(projects.feature.friends.usecase.integration)
    implementation(projects.feature.invitations.usecase.integration)
    implementation(projects.feature.meetings.usecase.integration)
    implementation(projects.feature.notifications.usecase.integration)
    implementation(projects.feature.updates.usecase.integration)
    implementation(projects.feature.users.usecase.integration)

    implementation(libs.ktor.server.core)
    implementation(libs.ktor.server.cio)

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
