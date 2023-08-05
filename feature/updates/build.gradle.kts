plugins {
    id("backend-convention")
}

dependencies {
    api(projects.feature.updates.database)
    api(projects.feature.updates.usecase)
    api(projects.feature.updates.endpoints)
    api(projects.feature.updates.database.integration)
    api(projects.feature.updates.usecase.integration)
}
