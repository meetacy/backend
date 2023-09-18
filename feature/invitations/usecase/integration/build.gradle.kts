plugins {
    id("backend-convention")
}

// usecase and endpoints dependencies
dependencies {
    api(projects.feature.invitations.usecase)
    api(projects.feature.invitations.database)

    api(projects.feature.users.usecase.integration)
    api(projects.feature.meetings.usecase.integration)
    api(projects.feature.notifications.usecase)

    // FIXME
    api(projects.feature.users.database.integration)
    api(projects.feature.meetings.database.integration)
}
