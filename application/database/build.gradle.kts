plugins {
    id("backend-convention")
}

dependencies {
    implementation(libs.mdi)

    implementation(projects.feature.auth.database.integrations)
    implementation(projects.feature.email.database.integrations)
    implementation(projects.feature.files.database.integrations)
    implementation(projects.feature.friends.database.integrations)
    implementation(projects.feature.invitations.database.integrations)
    implementation(projects.feature.meetings.database.integrations)
    implementation(projects.feature.notification.database.integrations)
    implementation(projects.feature.updates.database.integrations)
    implementation(projects.feature.user.database.integrations)
}
