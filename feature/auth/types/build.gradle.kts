plugins {
    id("backend-convention")
    id("serialization-convention")
}

dependencies {
//    api(projects.constants)

    implementation(libs.serializationGradle)
}
