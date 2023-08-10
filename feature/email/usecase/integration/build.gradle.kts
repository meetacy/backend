plugins {
    id("backend-convention")
}

// usecase and endpoints dependencies
dependencies {
    implementation(projects.core.integration)
    implementation(projects.core.types.serializable.integration)
    implementation(projects.feature.email.usecase)
    implementation(projects.feature.email.endpoints)
}
