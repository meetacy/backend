plugins {
    id("backend-convention")
}

dependencies {
    api(projects.core.database.integration)
    api(projects.feature.updates.usecase)
    api(projects.feature.notifications.usecase)
    api(projects.feature.notifications.database)
}
