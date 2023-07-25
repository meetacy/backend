plugins {
    id("backend-convention")
}

dependencies {
    api(projects.feature.notification.database)
    api(projects.feature.notification.usecase)
    api(projects.feature.notification.endpoints)
    api(projects.feature.notification.types)
    api(projects.feature.notification.database.integrations)
    api(projects.feature.notification.usecase.integrations)
}
