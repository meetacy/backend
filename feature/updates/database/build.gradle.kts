plugins {
    id("backend-convention")
}

dependencies {
    implementation(libs.exposed.core)

    api(projects.feature.users.database)
    api(projects.libs.exposedExtensions)
}
