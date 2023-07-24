plugins {
    id("backend-convention")
}

// usecase and endpoints dependencies
dependencies {
    api(projects.feature.user.usecase)
    api(projects.feature.user.types)
    api(projects.feature.user.endpoints)

    api(projects.feature.files.usecase)

    api(projects.feature.common)
}
