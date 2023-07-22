plugins {
    id("backend-convention")
}

dependencies {
    api(projects.feature.files.types)

    api(projects.feature.user.database)

    api(libs.exposedCore)
}
