plugins {
    id("backend-convention")
}

dependencies {
    api(projects.core.types)
    api(projects.libs.paging)
}
