plugins {
    id("backend-convention")
}

dependencies {
    api(projects.feature.users.usecase)
    api(projects.libs.stdlibExtensions)
    api(projects.libs.paging)
}
