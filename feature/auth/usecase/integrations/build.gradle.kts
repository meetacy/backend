plugins {
    id("backend-convention")
}

// usecase and endpoints dependencies
dependencies {
    api(projects.feature.auth.usecase)
    api(projects.feature.auth.endpoints)
    api(projects.core.typesSerializable.integration)
}
