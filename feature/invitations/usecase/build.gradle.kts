plugins {
    id("backend-convention")
}

dependencies {
    api(projects.feature.invitations.types)

    api(projects.feature.invitations.usecase)
}
