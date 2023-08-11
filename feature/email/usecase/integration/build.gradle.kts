plugins {
    id("backend-convention")
}

// usecase and endpoints dependencies
dependencies {
    implementation(projects.core.integration)
    implementation(projects.core.types.serializable.integration)
    api(projects.feature.email.usecase)
    api(projects.feature.email.endpoints)
}
