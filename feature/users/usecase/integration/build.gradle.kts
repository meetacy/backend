plugins {
    id("backend-convention")
}

// usecase and endpoints dependencies
dependencies {
    api(projects.feature.users.usecase)
    api(projects.core.types.serializable.integration)
    api(projects.feature.users.endpoints)

    api(projects.feature.files.usecase)

    api(projects.core)
}
