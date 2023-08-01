plugins {
    id("backend-convention")
}

dependencies {
    api(projects.feature.auth.database)
    api(projects.feature.auth.usecase)
    api(projects.core.types)

    api(libs.exposedCore)
}
