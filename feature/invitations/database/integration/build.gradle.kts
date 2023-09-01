plugins {
    id("backend-convention")
}

dependencies {
    api(projects.core.database.integration)
    implementation(projects.feature.invitations.database)
}
