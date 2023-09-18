plugins {
    id("backend-convention")
}

dependencies {
    implementation(projects.core.endpoints.integration)
    api(projects.feature.meetings.usecase)
    api(projects.feature.meetings.endpoints)
}
