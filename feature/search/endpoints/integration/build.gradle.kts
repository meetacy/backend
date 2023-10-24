plugins {
    id("backend-convention")
}

dependencies {
    implementation(projects.core.endpoints.integration)
    implementation(projects.core.types.serializable.integration)
    implementation(projects.feature.search.endpoints)
    implementation(projects.feature.search.usecase)
}
