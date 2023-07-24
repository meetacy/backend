plugins {
    id("backend-convention")
}

dependencies {
    api(projects.feature.invitations.types)

    api(projects.feature.auth.types)
    api(projects.feature.auth.usecase)
    api(projects.feature.meetings.types)
    api(projects.feature.meetings.usecase)

    api(projects.feature.common)
}
