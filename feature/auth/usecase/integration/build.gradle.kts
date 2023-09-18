plugins {
    id("backend-convention")
}

dependencies {
    implementation(projects.core.usecase.integration)
    api(projects.feature.auth.usecase)
    api(projects.feature.auth.database)

    implementation(projects.feature.users.database)
}
