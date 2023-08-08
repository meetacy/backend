plugins {
    id("backend-convention")
}

dependencies {
//    implementation(libs.meetacy.di.core)

    api(projects.feature.auth.database.integration)
    api(projects.feature.email.database.integration)
    api(projects.feature.files.database.integration)
    api(projects.feature.friends.database.integration)
    api(projects.feature.invitations.database.integration)
    api(projects.feature.meetings.database.integration)
    api(projects.feature.notifications.database.integration)
    api(projects.feature.updates.database.integration)
    api(projects.feature.user.database.integration)
}
