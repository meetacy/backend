plugins {
    id("backend-convention")
}

dependencies {
    api(projects.feature.meetings.database)
    api(projects.feature.meetings.usecase)
    api(projects.feature.meetings.endpoints)
    api(projects.feature.meetings.database.integrations)
    api(projects.feature.meetings.usecase.integrations)
}
