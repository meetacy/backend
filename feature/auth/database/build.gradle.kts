plugins {
    id("backend-convention")
}

dependencies {
    api(projects.feature.auth.types)
    implementation(libs.exposedCore)
}
