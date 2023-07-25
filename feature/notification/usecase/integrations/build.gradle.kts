plugins {
    id("backend-convention")
}

// usecase and endpoints dependencies
dependencies {
    api(projects.feature.notification.usecase)
    api(projects.feature.notification.endpoints)
    api(projects.feature.notification.types)

    api(projects.feature.meetings.usecase.integrations)
    api(projects.feature.user.usecase.integrations)
}
