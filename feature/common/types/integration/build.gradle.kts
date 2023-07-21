plugins {
    id("backend-convention")
}

dependencies {
    api(projects.libs.hashGenerator)
    api(projects.feature.common.types)
    implementation(libs.mdi)
}
