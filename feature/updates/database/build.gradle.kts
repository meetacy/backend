plugins {
    id("backend-convention")
}

dependencies {
    api(projects.feature.updates.types)
    implementation(libs.exposedCore)

    api(projects.feature.user.database)
    api(projects.libs.exposedExtensions)
}
