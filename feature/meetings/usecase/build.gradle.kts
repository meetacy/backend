plugins {
    id("backend-convention")
}

dependencies {

    api(projects.feature.files.usecase)
    api(projects.feature.user.usecase)
    api(projects.libs.paging.types)

    implementation(libs.kotlinx.coroutines.core)
    api(projects.libs.stdlibExtensions)
}
