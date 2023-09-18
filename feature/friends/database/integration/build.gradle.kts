plugins {
    id("backend-convention")
}

dependencies {
    api(projects.feature.friends.usecase)
    api(projects.feature.friends.database)
    api(projects.feature.notifications.usecase)

    api(projects.feature.users.database.integration)
}
