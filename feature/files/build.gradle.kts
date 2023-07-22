plugins {
    id("backend-convention")
}

dependencies {
    api(projects.feature.files.database)
    api(projects.feature.files.usecase)
    api(projects.feature.files.endpoints)
    api(projects.feature.files.types)
    api(projects.feature.files.database.integrations)
    api(projects.feature.files.usecase.integrations)
}
