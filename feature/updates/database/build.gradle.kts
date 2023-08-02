plugins {
    id("backend-convention")
}

dependencies {
    implementation(libs.exposedCore)

    api(projects.feature.user.database)
    api(projects.libs.exposedExtensions)
}
