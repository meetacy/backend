plugins {
    id("backend-convention")
}

dependencies {
    implementation(libs.meetacy.di.core)
    implementation(projects.feature.auth.usecase.integration)
    implementation(projects.feature.auth.telegram.usecase.integration)
    implementation(projects.feature.auth.email.usecase.integration)
    implementation(projects.feature.files.usecase.integration)
    implementation(projects.feature.friends.usecase.integration)
    implementation(projects.feature.invitations.usecase.integration)
    implementation(projects.feature.meetings.usecase.integration)
    implementation(projects.feature.notifications.usecase.integration)
    implementation(projects.feature.search.usecase.integration)
    implementation(projects.feature.updates.usecase.integration)
    implementation(projects.feature.users.usecase.integration)
}
