plugins {
    id("backend-convention")
}

dependencies {
    api(projects.libs.paging.types)

    api(libs.exposedCore)
}
