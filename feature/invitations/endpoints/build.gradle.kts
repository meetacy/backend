plugins {
    id("backend-convention")
    id("serialization-convention")
}

dependencies {
    api(projects.libs.ktorExtensions)
    api(projects.feature.meetings.endpoints)

    implementation(libs.kotlinx.serialization.json)
}
