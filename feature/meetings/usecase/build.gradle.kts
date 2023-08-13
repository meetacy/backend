plugins {
    id("backend-convention")
}

dependencies {
    implementation(projects.core.usecase)

    api(projects.feature.files.usecase)
    api(projects.feature.users.usecase)
    api(projects.libs.paging)

    implementation(libs.kotlinx.coroutines.core)
    api(projects.libs.stdlibExtensions)
}
