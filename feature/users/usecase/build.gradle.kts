plugins {
    id("backend-convention")
}

dependencies {
    implementation(projects.core.usecase)
    implementation(projects.feature.files.usecase)
    implementation(projects.feature.auth.usecase)
}
