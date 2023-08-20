plugins {
    id("backend-convention")
}

dependencies {
    implementation(projects.core.usecase.integration)
    api(projects.feature.files.database)
    api(projects.feature.files.usecase)
}
