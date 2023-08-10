plugins {
    id("backend-convention")
}

dependencies {
    api(projects.libs.paging)
    api(libs.exposed.core)
}
