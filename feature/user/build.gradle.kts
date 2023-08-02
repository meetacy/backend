plugins {
    id("backend-convention")
}

dependencies {
    api(projects.feature.user.usecase)
    api(projects.feature.user.database)
    api(projects.feature.user.endpoints)
    api(projects.feature.user.database.integrations)
    api(projects.feature.user.usecase.integrations)
}
