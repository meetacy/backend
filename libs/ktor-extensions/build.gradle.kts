plugins {
    id("backend-convention")
    id("serialization-convention")
}

dependencies {
    api(libs.ktorServer.core)
    api(libs.ktorServer.cio)
    api(libs.ktorServer.rsocket)

    implementation(libs.ktorServer.websockets)
    implementation(libs.ktorServer.swagger)
    implementation(libs.ktorServer.partialContent)
    implementation(libs.ktorServer.cors)
    implementation(libs.ktorServer.autoHead)
    implementation(libs.ktorServer.serializationJson)
    implementation(libs.ktorServer.contentNegotiation)
    implementation(libs.ktorServer.statusPages)
    implementation(libs.serializationGradle)
    implementation(libs.ktorServer.requestValidation)
    implementation(libs.ktorServer.serverStatusPage)
    implementation(libs.slf4jSimple)
}
