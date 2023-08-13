plugins {
    id("backend-convention")
}

// usecase and endpoints dependencies
dependencies {
    api(projects.feature.friends.usecase)
    api(projects.feature.friends.endpoints)

    api(projects.feature.users.usecase.integration)

    api(projects.libs.paging.serializable.integration)
    api(projects.libs.paging.serializable)
}
