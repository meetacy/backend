plugins {
    id("backend-convention")
}

dependencies {
    implementation(projects.core.endpoints.integration)
    implementation(projects.core.types.serializable.integration)
    implementation(projects.feature.auth.telegram.endpoints)
    implementation(projects.feature.auth.telegram.usecase)
}
