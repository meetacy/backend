plugins {
    id("backend-convention")
}

dependencies {
    api(projects.feature.email.types)
    api(libs.exposedCore)

    api(projects.feature.user.database)
}
