plugins {
    id("backend-convention")
}

dependencies {
    api(projects.feature.notifications.usecase)
}
