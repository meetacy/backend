plugins {
    id("backend-convention")
}

dependencies {
    api(projects.feature.invitations.database)
    api(projects.feature.invitations.usecase)
    api(projects.feature.invitations.endpoints)
    api(projects.feature.invitations.types)
    api(projects.feature.invitations.database.integrations)
    api(projects.feature.invitations.usecase.integrations)
}
