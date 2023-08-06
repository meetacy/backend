plugins {
    id("backend-convention")
}

dependencies {
    api(projects.feature.email.usecase)
    api(projects.feature.email.database)
}
