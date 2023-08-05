plugins {
    id("backend-convention")
}

dependencies {
    api(projects.application.database)

    implementation(libs.meetacy.di.core)

    implementation(projects.feature.auth.usecase.integration)
    implementation(projects.feature.email.usecase.integration)
    implementation(projects.feature.files.usecase.integration)
    implementation(projects.feature.friends.usecase.integration)
    implementation(projects.feature.invitations.usecase.integration)
    implementation(projects.feature.meetings.usecase.integration)
    implementation(projects.feature.notifications.usecase.integration)
    implementation(projects.feature.updates.usecase.integration)
    implementation(projects.feature.user.usecase.integration)

    implementation(projects.libs.utf8Checker)
}
