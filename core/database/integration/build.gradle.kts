plugins {
    id("backend-convention")
}

dependencies {
    api(projects.core.integration)
    api(libs.exposed.core)
}
