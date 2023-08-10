plugins {
    id("backend-convention")
    id("serialization-convention")
}

dependencies {
    api(projects.core.types.serializable.integration)
    api(projects.feature.user.endpoints)

    api(projects.libs.ktorExtensions)

    api(projects.libs.paging)

    implementation(libs.kotlinx.serialization.json)
    implementation(libs.meetacy.di.core)
    implementation(libs.meetacy.di.global)
}
