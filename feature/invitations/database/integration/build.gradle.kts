plugins {
    id("backend-convention")
}

dependencies {
    api(projects.feature.invitations.usecase)
    api(projects.feature.invitations.database)

    api(projects.feature.friends.database)
    api(projects.feature.notifications.usecase)
    api(projects.feature.meetings.database.integration)
    api(projects.feature.user.database.integration)
}
