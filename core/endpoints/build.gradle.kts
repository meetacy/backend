plugins {
    id("backend-convention")
}

dependencies {
    api(projects.libs.ktorExtensions)
    api(projects.core.types.serializable)
    api(projects.libs.paging.serializable)
    api(libs.ktor.server.core)
    api(libs.kotlinx.serialization.json)
}
