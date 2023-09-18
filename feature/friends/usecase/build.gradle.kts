plugins {
    id("backend-convention")
}

dependencies {
    implementation(projects.core.usecase)
    api(projects.feature.users.usecase)
    api(projects.libs.stdlibExtensions)
    api(projects.libs.paging)
}
