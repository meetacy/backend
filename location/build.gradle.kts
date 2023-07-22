plugins {
    id("backend-convention")
    id("serialization-convention")
}

dependencies {
    api(projects.datetime)
    implementation(libs.serializationGradle)
}
