plugins {
    id("backend-convention")
    id("serialization-convention")
}

dependencies {
    api(projects.libs.ktorExtensions)
    api(projects.core.typesSerializable.integration)
    implementation(projects.core.types)

    implementation(libs.ktor.server.core)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.meetacy.di.global)
}
