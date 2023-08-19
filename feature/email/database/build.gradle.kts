plugins {
    id("backend-convention")
}

dependencies {
    api(projects.feature.users.database)
    implementation(libs.exposed.core)
}
