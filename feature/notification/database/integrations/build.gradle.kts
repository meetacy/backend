plugins {
    id("backend-convention")
}

dependencies {
    api(projects.feature.notification.usecase)
    api(projects.feature.notification.database)
    api(projects.feature.updates.usecase)
}
