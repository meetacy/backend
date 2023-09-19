plugins {
    id("backend-convention")
    id("serialization-convention")
}

dependencies {
    implementation(projects.core.endpoints)
}
