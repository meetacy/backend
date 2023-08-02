plugins {
    id("backend-convention")
}

dependencies {
    api(projects.feature.email.types)
    api(libs.exposed.core)

    api(projects.feature.user.database)
}
