plugins {
    id("backend-convention")
}

dependencies {
    implementation(libs.exposed.core)
    api(projects.feature.users.database)
    api(projects.feature.meetings.database)
    api(projects.libs.paging)
    api(projects.libs.paging.database)
}
