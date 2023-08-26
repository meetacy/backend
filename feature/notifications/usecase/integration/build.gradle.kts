plugins {
    id("backend-convention")
}

// usecase and endpoints dependencies
dependencies {
    implementation(projects.core.usecase.integration)

    api(projects.feature.notifications.database)
    api(projects.feature.notifications.usecase)

    api(projects.feature.updates.usecase)

    api(projects.libs.paging.serializable.integration)
}
