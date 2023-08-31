plugins {
    id("backend-convention")
}

dependencies {
    api(projects.libs.hashGenerator)
    api(projects.libs.utf8Checker)
    api(projects.core.types)

    implementation(projects.feature.auth.database)
    implementation(projects.feature.files.database)
    implementation(projects.feature.meetings.database)

    implementation(projects.feature.meetings.usecase)
    implementation(projects.feature.notifications.usecase)
    implementation(projects.feature.users.usecase)

    implementation(libs.meetacy.di.core)
    implementation(libs.exposed.core)
}
