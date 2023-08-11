plugins {
    id("backend-convention")
}

// usecase and endpoints dependencies
dependencies {
    api(projects.core.endpoints.integration)
    api(projects.feature.meetings.usecase)
    api(projects.feature.meetings.endpoints)
    api(projects.libs.paging.serializable)
}
