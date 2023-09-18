plugins {
    id("backend-convention")
}

dependencies {
    implementation(projects.feature.email.endpoints.integration)
    implementation(projects.core.endpoints.integration)
    api(projects.feature.auth.usecase)
    api(projects.feature.auth.endpoints)
}
