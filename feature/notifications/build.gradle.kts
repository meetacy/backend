plugins {
    id("backend-convention")
}

dependencies {
    api(projects.feature.notifications.database)
    api(projects.feature.notifications.usecase)
    api(projects.feature.notifications.endpoints)
    api(projects.feature.notifications.database.integration)
    api(projects.feature.notifications.usecase.integration)
}
