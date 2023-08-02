plugins {
    id("backend-convention")
}

dependencies {

    api(projects.feature.user.database)

    api(libs.exposed.core)
}
