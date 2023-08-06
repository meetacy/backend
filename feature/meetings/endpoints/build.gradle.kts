plugins {
    id("backend-convention")
    id("serialization-convention")
}

dependencies {
    api(projects.core.typesSerializable.integration)
    api(projects.feature.user.endpoints)

    api(projects.libs.ktorExtensions)

    api(projects.libs.paging.types)

    implementation(libs.kotlinx.serialization.json)
}
