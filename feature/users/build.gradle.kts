plugins {
    id("backend-convention")
}

dependencies {
    api(projects.feature.users.usecase)
    api(projects.feature.users.database)
    api(projects.feature.users.endpoints)
    api(projects.feature.users.database.integration)
    api(projects.feature.users.usecase.integration)
}
