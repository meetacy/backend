plugins {
    id("backend-convention")
}

dependencies {
    api(projects.feature.meetings.types)
    implementation(libs.exposed.core)

    api(projects.libs.paging.types)
    api(projects.libs.exposedExtensions)

    api(projects.feature.files.database)
}
