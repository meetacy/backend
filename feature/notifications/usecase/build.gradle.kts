plugins {
    id("backend-convention")
}

dependencies {
    api(projects.feature.user.usecase)
    api(projects.feature.meetings.usecase)
}
