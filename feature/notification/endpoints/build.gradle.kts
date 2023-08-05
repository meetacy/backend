plugins {
    id("backend-convention")
    id("serialization-convention")
}

dependencies {
    api(projects.libs.ktorExtensions)
    api(projects.core.typesSerializable)
    api(projects.libs.paging.types)

    implementation(libs.ktor.server.core)
    implementation(libs.kotlinx.serialization.json)
    implementation(project(mapOf("path" to ":feature:user:endpoints")))
}
