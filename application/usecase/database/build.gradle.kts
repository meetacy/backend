plugins {
    id("backend-convention")
}

dependencies {
    implementation(libs.mdi)

    implementation(projects.feature.auth.database)
    implementation(projects.feature.email.database)
    implementation(projects.feature.files.database)
    implementation(projects.feature.friends.database)
    implementation(projects.feature.invitations.database)
    implementation(projects.feature.meetings.database)
    implementation(projects.feature.notification.database)
    implementation(projects.feature.updates.database)
    implementation(projects.feature.user.database)
}
