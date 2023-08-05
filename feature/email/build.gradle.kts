plugins {
    id("backend-convention")
}

dependencies {
    api(projects.feature.email.database)
    api(projects.feature.email.usecase)
    api(projects.feature.email.endpoints)

    api(projects.feature.email.database.integration)
    api(projects.feature.email.usecase.integration)
}
