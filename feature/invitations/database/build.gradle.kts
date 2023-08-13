plugins {
    id("backend-convention")
}

dependencies {
    implementation(libs.exposed.core)
    api(projects.feature.meetings.database)
    api(projects.feature.users.database)
}
