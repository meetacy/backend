plugins {
    id("backend-convention")
}

dependencies {

    api(projects.core.types)
    api(projects.feature.auth.usecase)
    api(projects.feature.meetings.usecase)

    api(projects.core)
}
