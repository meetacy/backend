plugins {
    id("backend-convention")
}

dependencies {
    api(projects.core.endpoints.integration)

    api(projects.feature.notifications.endpoints)
    api(projects.feature.notifications.usecase)
}
