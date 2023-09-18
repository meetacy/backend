plugins {
    id("backend-convention")
}

dependencies {
    api(projects.libs.paging)
    api(projects.libs.paging.serializable)
}
