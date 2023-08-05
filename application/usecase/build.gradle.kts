plugins {
    id("backend-convention")
}

dependencies {
    api(projects.application.database)

    implementation(libs.meetacy.di.core)

    implementation(projects.feature.auth.usecase.integrations)
    implementation(projects.feature.email.usecase.integrations)
    implementation(projects.feature.files.usecase.integrations)
    implementation(projects.feature.friends.usecase.integrations)
    implementation(projects.feature.invitations.usecase.integrations)
    implementation(projects.feature.meetings.usecase.integrations)
    implementation(projects.feature.notification.usecase.integrations)
    implementation(projects.feature.updates.usecase.integrations)
    implementation(projects.feature.user.usecase.integrations)

    implementation(projects.libs.utf8Checker.usecaseIntegration)
}
