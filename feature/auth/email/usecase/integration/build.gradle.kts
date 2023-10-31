plugins {
    id("backend-convention")
}

dependencies {
    implementation(projects.core.usecase.integration)
    implementation(projects.feature.auth.email.usecase)
    implementation(projects.feature.auth.email.database)
}
