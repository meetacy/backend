plugins {
    id("backend-convention")
}

dependencies {
    api(projects.feature.updates.usecase)
    api(projects.feature.updates.database)
}
