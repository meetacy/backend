plugins {
    id("backend-convention")
}

dependencies {
    api(projects.feature.updates.types)
    api(projects.feature.notification.usecase)
}
