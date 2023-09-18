plugins {
    id("backend-convention")
}

dependencies {
    implementation(libs.exposed.core)

    api(projects.libs.paging)
    api(projects.libs.exposedExtensions)

    api(projects.feature.files.database)
}
