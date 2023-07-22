plugins {
    id("backend-convention")
}

dependencies {
    api(projects.feature.meetings.types)
    implementation(libs.exposedCore)

    api(projects.libs.paging.types)
    api(projects.libs.exposedExtensions)

    api(projects.feature.files.database)
}
