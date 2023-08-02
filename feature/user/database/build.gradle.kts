plugins {
    id("backend-convention")
}

dependencies {
    api(projects.feature.user.types)
    api(projects.core.types)
    api(projects.feature.files.types)

    implementation(libs.exposed.core)
}
