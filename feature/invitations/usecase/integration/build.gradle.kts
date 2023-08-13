plugins {
    id("backend-convention")
}

// usecase and endpoints dependencies
dependencies {
    api(projects.feature.invitations.usecase)
    api(projects.feature.invitations.endpoints)

    api(projects.feature.users.usecase.integration)
    api(projects.feature.meetings.usecase.integration)
}
