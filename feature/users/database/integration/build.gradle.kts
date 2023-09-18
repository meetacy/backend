plugins {
    id("backend-convention")
}

dependencies {
    api(projects.feature.users.usecase)
    api(projects.feature.users.database)
    api(projects.feature.friends.database)

    api(projects.feature.files.database.integration)
    api(projects.feature.auth.database)

    api(projects.core)
    implementation(libs.exposed.core)
}
