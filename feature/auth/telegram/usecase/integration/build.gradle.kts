plugins {
    id("backend-convention")
}

dependencies {
    implementation(projects.libs.hashGenerator)
    implementation(projects.core.usecase.integration)
    implementation(projects.feature.auth.telegram.usecase)
    implementation(projects.feature.auth.telegram.usecase)
    implementation(projects.feature.auth.telegram.database)
}
