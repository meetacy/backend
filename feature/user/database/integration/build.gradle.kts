plugins {
    id("backend-convention")
}

dependencies {
    api(projects.feature.user.usecase)
    api(projects.feature.user.database)
    api(projects.feature.friends.database)

    api(projects.feature.files.database.integration)
    api(projects.feature.auth.database)

    api(projects.core)
}
