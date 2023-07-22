plugins {
    id("backend-convention")
}

// usecase and endpoints dependencies
dependencies {
    api(projects.feature.invitations.usecase)
    api(projects.feature.invitations.endpoints)
    api(projects.feature.invitations.types)

    api(projects.feature.user.usecase.integrations)
    api(projects.feature.meetings.usecase.integrations)
}
