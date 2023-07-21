plugins {
    id("backend-convention")
}

dependencies {
    implementation(libs.exposedCore)
    api(projects.feature.invitations.types)
    api(projects.feature.meetings.database)
    api(projects.feature.user.database)
}
