plugins {
    id("backend-convention")
}

dependencies {
    api(projects.feature.invitations.types)

    api(projects.core.types)
    api(projects.feature.auth.usecase)
    api(projects.feature.meetings.types)
    api(projects.feature.meetings.usecase)

    api(projects.core)
}
