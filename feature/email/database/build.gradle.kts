plugins {
    id("backend-convention")
}

dependencies {
    api(libs.exposed.core)

    api(projects.feature.users.database)
}
