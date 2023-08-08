plugins {
    id("backend-convention")
    id("serialization-convention")
}

dependencies {
    implementation(projects.core.endpoints)
    implementation(projects.feature.email.endpoints)
}
