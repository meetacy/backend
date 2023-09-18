plugins {
    id("backend-convention")
}

dependencies {
    implementation(projects.core.usecase.integration)
    implementation(projects.feature.email.usecase)
    implementation(projects.feature.email.database)
}
