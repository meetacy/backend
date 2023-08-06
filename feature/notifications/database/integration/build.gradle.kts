plugins {
    id("backend-convention")
}

dependencies {
    api(projects.feature.notifications.usecase)
    api(projects.feature.notifications.database)
    api(projects.feature.updates.usecase)
}
