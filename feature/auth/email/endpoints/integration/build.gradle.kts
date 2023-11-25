plugins {
    id("backend-convention")
}

dependencies {
    implementation(projects.core.endpoints.integration)
    api(projects.feature.auth.email.usecase)
    api(projects.feature.auth.email.endpoints)
}
