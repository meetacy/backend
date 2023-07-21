plugins {
    id("backend-convention")
}

dependencies {
    api(projects.libs.paging.types)
    api(projects.libs.paging.database)
}
