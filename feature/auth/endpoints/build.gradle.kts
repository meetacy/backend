plugins {
    id("backend-convention")
    id("serialization-convention")
}

dependencies {
    api(projects.core.typesSerializable)
    api(projects.libs.ktorExtensions)
    implementation(libs.ktor.server.core)
    implementation(libs.kotlinx.serialization.json)
    api(libs.meetacy.di.global)
    implementation(libs.meetacy.di.core)
}
