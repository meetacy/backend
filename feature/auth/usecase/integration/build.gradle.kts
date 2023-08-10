plugins {
    id("backend-convention")
}

// usecase and endpoints dependencies
dependencies {
    implementation(projects.core.integration)
    api(projects.core.types.serializable.integration)
    api(projects.feature.auth.usecase)
    api(projects.feature.auth.endpoints)
}
