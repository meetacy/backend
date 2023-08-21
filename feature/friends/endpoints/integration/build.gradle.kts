plugins {
    id("backend-convention")
}

dependencies {
    api(projects.feature.friends.endpoints)
    api(projects.feature.friends.usecase)

    implementation(projects.core.endpoints.integration)

    implementation(libs.ktor.server.core)
}
