plugins {
    id("backend-convention")
}

// usecase and endpoints dependencies
dependencies {
    api(projects.feature.friends.usecase)
    api(projects.feature.friends.endpoints)

    api(projects.feature.user.usecase.integration)
}
