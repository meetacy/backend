plugins {
    id("backend-convention")
    id("serialization-convention")
}

dependencies {
    api(projects.libs.ktorExtensions)
    api(projects.core.types.serializable.integration)
    api(projects.libs.paging)
    api(projects.feature.user.endpoints)
    api(projects.feature.meetings.endpoints)

    implementation(libs.ktor.server.core)
    implementation(libs.kotlinx.serialization.json)
}
