plugins {
    id("backend-convention")
}

dependencies {
    implementation(libs.exposedCore)

    api(projects.libs.paging.types)
    api(projects.libs.exposedExtensions)

    api(projects.feature.files.database)
}
