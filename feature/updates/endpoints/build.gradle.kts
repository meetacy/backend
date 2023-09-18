plugins {
    id("backend-convention")
    id("serialization-convention")
}

dependencies {
    api(projects.libs.ktorExtensions)
    api(projects.feature.notifications.endpoints)

    implementation(libs.ktor.server.core)
    implementation(libs.kotlinx.serialization.json)
}
