plugins {
    id("backend-convention")
}

dependencies {
    api(projects.feature.notification.usecase)
}
