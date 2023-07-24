plugins {
    id("backend-convention")
}

dependencies {
    api(projects.feature.auth.database)
    api(projects.feature.email.database)
    api(projects.feature.files.database)
    api(projects.feature.friends.database)
    api(projects.feature.invitations.database)
    api(projects.feature.meetings.database)
    api(projects.feature.notification.database)
    api(projects.feature.updates.database)
    api(projects.feature.user.database)

    implementation(libs.exposedCore)
    api(libs.wdater.core)
    api(libs.wdater.autoMigrations)
}
