plugins {
    id("backend-convention")
}

dependencies {
    implementation(libs.exposed.core)

    api(projects.libs.paging.types)
    api(projects.libs.exposedExtensions)

    api(projects.feature.files.database)
}
