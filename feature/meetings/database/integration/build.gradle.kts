plugins {
    id("backend-convention")
}

dependencies {
    api(projects.feature.meetings.usecase)
    api(projects.feature.meetings.database)
    api(projects.feature.files.database.integration)
    api(projects.feature.users.database.integration)
}
