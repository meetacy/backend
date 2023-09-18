plugins {
    id("backend-convention")
    id("serialization-convention")
}

dependencies {
    api(projects.core.types)
    api(projects.libs.paging)
    api(projects.core.types.serializable)

    implementation(libs.kotlinx.serialization.json)
}
