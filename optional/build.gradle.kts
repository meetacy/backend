plugins {
    id("backend-convention")
    id("serialization-convention")
}

dependencies {
    implementation(libs.serializationGradle)
}
