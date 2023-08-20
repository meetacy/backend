plugins {
    id("backend-convention")
}

dependencies {
    implementation(projects.core.endpoints.integration)
    api(projects.feature.email.usecase)
    api(projects.feature.email.endpoints)
}
