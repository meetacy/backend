plugins {
    id("backend-convention")
}

dependencies {
    api(projects.application.database.migrations)
    implementation(projects.feature.auth.database.integration)
    implementation(projects.feature.auth.email.database.integration)
    implementation(projects.feature.files.database.integration)
    implementation(projects.feature.friends.database.integration)
    implementation(projects.feature.invitations.database.integration)
    implementation(projects.feature.meetings.database.integration)
    implementation(projects.feature.notifications.database.integration)
    implementation(projects.feature.updates.database.integration)
    implementation(projects.feature.users.database.integration)
}
