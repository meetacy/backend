plugins {
    id("backend-convention")
    id("serialization-convention")
}

dependencies {
    api(projects.core.types)
    api(projects.core.types.serializable)
    api(projects.libs.ktorExtensions)

    implementation(libs.ktor.server.core)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.meetacy.di.core)
    implementation(projects.core.endpoints)
}
