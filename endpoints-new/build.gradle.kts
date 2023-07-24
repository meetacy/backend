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

    api(libs.ktorServer.core)
    api(libs.ktorServer.cio)

    implementation(libs.ktorServer.websockets)
    implementation(libs.ktorServer.rsocket)
    implementation(libs.ktorServer.swagger)
    implementation(libs.ktorServer.partialContent)
    implementation(libs.ktorServer.cors)
    implementation(libs.ktorServer.autoHead)
    implementation(libs.ktorServer.serializationJson)
    implementation(libs.ktorServer.contentNegotiation)
    implementation(libs.ktorServer.statusPages)
    implementation(libs.serializationJson)
    implementation(libs.slf4jSimple)
    implementation(libs.ktorServer.requestValidation)
    implementation(libs.ktorServer.serverStatusPage)
}
