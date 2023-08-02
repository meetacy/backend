plugins {
    id("backend-convention")
    id("serialization-convention")
}

dependencies {
    api(projects.feature.files.types)
    api(projects.libs.ktorExtensions)

    implementation(libs.ktor.server.core)
    implementation(libs.kotlinx.serialization.json)
}
