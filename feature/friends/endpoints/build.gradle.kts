plugins {
    id("backend-convention")
    id("serialization-convention")
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-debug:1.6.0")
    implementation(projects.core.endpoints)
}
