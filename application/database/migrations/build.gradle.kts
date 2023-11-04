plugins {
    id("backend-convention")
}

dependencies {
    api(projects.feature.auth.database)
    api(projects.feature.auth.email.database)
    api(projects.feature.auth.telegram.database)
    api(projects.feature.files.database)
    api(projects.feature.friends.database)
    api(projects.feature.invitations.database)
    api(projects.feature.meetings.database)
    api(projects.feature.notifications.database)
    api(projects.feature.updates.database)
    api(projects.feature.users.database)

    implementation(libs.exposed.core)
    api(libs.meetacy.wdater.core)
    api(libs.meetacy.wdater.autoMigrations)
}
