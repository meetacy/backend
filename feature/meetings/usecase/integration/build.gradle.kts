plugins {
    id("backend-convention")
}

dependencies {
    api(projects.core.database.integration)
    api(projects.feature.meetings.usecase)
    api(projects.feature.meetings.database)
}
