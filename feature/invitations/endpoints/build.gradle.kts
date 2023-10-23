plugins {
    id("backend-convention")
    id("serialization-convention")
}

dependencies {
    api(projects.libs.ktorExtensions)
    api(projects.feature.meetings.endpoints)
    api(projects.core.types.serializable)

    implementation(libs.kotlinx.serialization.json)
    implementation(projects.core.endpoints)
}
