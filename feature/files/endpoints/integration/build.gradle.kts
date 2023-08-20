plugins {
    id("backend-convention")
}

dependencies {
    implementation(projects.core.endpoints.integration)
    api(projects.feature.files.usecase)
    api(projects.feature.files.database)
    api(projects.feature.files.endpoints)
}
