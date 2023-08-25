plugins {
    id("backend-convention")
}

// usecase and endpoints dependencies
dependencies {
    api(projects.feature.users.usecase)
    api(projects.feature.users.database)

    api(projects.feature.files.usecase)
    api(projects.feature.friends.database)

    api(projects.core.types)
    api(projects.core)
}
