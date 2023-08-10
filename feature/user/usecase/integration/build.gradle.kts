plugins {
    id("backend-convention")
}

// usecase and endpoints dependencies
dependencies {
    api(projects.feature.user.usecase)
    api(projects.core.types.serializable.integration)
    api(projects.feature.user.endpoints)

    api(projects.feature.files.usecase)

    api(projects.core)
}
