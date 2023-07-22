plugins {
    id("backend-convention")
}

dependencies {
    api(projects.feature.user.database)
    api(projects.libs.paging)
}
