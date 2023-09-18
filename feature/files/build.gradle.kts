plugins {
    id("backend-convention")
}

dependencies {
    api(projects.feature.files.database)
    api(projects.feature.files.usecase)
    api(projects.feature.files.endpoints)
    api(projects.feature.files.database.integration)
    api(projects.feature.files.usecase.integration)
}
