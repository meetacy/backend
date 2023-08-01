plugins {
    id("backend-convention")
}

dependencies {
    api(projects.feature.auth.database)
    api(projects.feature.auth.database.integrations)
    api(projects.feature.auth.endpoints)
    api(projects.core.types)
    api(projects.feature.auth.usecase)
    api(projects.feature.auth.usecase.integrations)
}
