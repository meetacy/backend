plugins {
    id("backend-convention")
}

// usecase and endpoints dependencies
dependencies {
    api(projects.core.usecase.integration)
    implementation(projects.feature.updates.usecase)
    implementation(projects.feature.updates.database)
}
