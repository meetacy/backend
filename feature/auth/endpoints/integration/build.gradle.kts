plugins {
    id("backend-convention")
}

dependencies {
    implementation(projects.feature.auth.email.endpoints.integration)
    implementation(projects.feature.auth.telegram.endpoints.integration)
    implementation(projects.core.endpoints.integration)
    api(projects.feature.auth.usecase)
    api(projects.feature.auth.endpoints)
}
