plugins {
    id("backend-convention")
}

dependencies {
    api(projects.core.endpoints.integration)
    implementation(projects.feature.users.endpoints)
    implementation(projects.feature.users.usecase)
}
