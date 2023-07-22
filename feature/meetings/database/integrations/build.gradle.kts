plugins {
    id("backend-convention")
}

dependencies {
    api(projects.feature.meetings.usecase)
    api(projects.feature.meetings.database)
    api(projects.feature.files.database.integrations)
    api(projects.feature.user.database.integrations)
}
