plugins {
    id("backend-convention")
}

dependencies {
    api(projects.feature.user.usecase)
    api(projects.libs.stdlibExtensions)
    api(projects.libs.paging)
}
