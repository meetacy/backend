plugins {
    id("backend-convention")
}

// usecase and endpoints dependencies
dependencies {
    api(projects.feature.user.usecase)
    api(projects.feature.user.types)
    api(projects.feature.user.database)

    api(projects.feature.files.usecase)
}
