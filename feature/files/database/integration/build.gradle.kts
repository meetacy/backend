plugins {
    id("backend-convention")
}

dependencies {
    api(projects.feature.files.usecase)
    api(projects.feature.files.database)
    api(projects.feature.files.endpoints)
}
