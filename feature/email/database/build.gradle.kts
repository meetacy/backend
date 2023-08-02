plugins {
    id("backend-convention")
}

dependencies {
    api(libs.exposedCore)

    api(projects.feature.user.database)
}
