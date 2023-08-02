plugins {
    id("backend-convention")
}

dependencies {
    api(projects.core.types)

    implementation(libs.exposedCore)
}
