plugins {
    id("backend-convention")
}

dependencies {
    api(projects.core.types)
    api(projects.libs.paging)
    api(libs.exposed.core)
    api(libs.kotlinx.coroutines.core)
    api(projects.libs.exposedExtensions)
}
