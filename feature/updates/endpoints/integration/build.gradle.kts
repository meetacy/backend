plugins {
    id("backend-convention")
}

dependencies {
    api(projects.core.endpoints.integration)

    implementation(projects.feature.updates.endpoints)
    implementation(projects.feature.updates.usecase)
}
