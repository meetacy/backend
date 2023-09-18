plugins {
    id("backend-convention")
}

dependencies {

    api(projects.feature.users.database)

    api(libs.exposed.core)
}
