plugins {
    id("backend-convention")
}

dependencies {
    implementation(projects.core.endpoints.integration)
    api(projects.feature.auth.usecase)
    api(projects.feature.auth.endpoints)
}
