plugins {
    id("backend-convention")
}

// usecase and endpoints dependencies
dependencies {
    api(projects.feature.meetings.usecase)
    api(projects.feature.meetings.endpoints)
    api(projects.feature.meetings.types)

    api(projects.feature.user.usecase.integrations)
}
