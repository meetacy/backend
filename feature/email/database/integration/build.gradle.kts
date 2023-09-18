plugins {
    id("backend-convention")
}

dependencies {
    implementation(projects.core.database.integration)
    implementation(projects.feature.email.database)
}
