plugins {
    id("backend-convention")
}

// usecase and endpoints dependencies
dependencies {
    api(projects.feature.notifications.usecase)
    api(projects.feature.notifications.endpoints)

    api(projects.feature.meetings.usecase.integration)
    api(projects.feature.user.usecase.integration)
}
