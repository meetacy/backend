plugins {
    id("backend-convention")
}

dependencies {
    api(projects.feature.updates.database)
    api(projects.feature.updates.usecase)
    api(projects.feature.updates.endpoints)
    api(projects.feature.updates.types)
    api(projects.feature.updates.database.integrations)
    api(projects.feature.updates.usecase.integrations)
}
