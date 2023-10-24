plugins {
    id("backend-convention")
}

dependencies {
    implementation(projects.core.usecase.integration)
    implementation(projects.feature.search.usecase)
    implementation(projects.feature.search.database)
}
