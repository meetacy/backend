plugins {
    id("backend-convention")
}

dependencies {
    api(projects.feature.user.types)
    api(projects.feature.auth.types)
    api(projects.feature.files.types)

    implementation(libs.exposedCore)
}
