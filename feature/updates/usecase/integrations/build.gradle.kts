plugins {
    id("backend-convention")
}

// usecase and endpoints dependencies
dependencies {
    api(projects.feature.updates.usecase)
    api(projects.feature.updates.endpoints)
    api(projects.feature.notification.usecase.integrations)
}
