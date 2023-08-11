plugins {
    id("backend-convention")
}

dependencies {
    api(projects.feature.notifications.usecase)
    api(projects.core.types)
}
