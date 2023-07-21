plugins {
    id("backend-convention")
    id("serialization-convention")
}

dependencies {
    api(projects.annotations)
    api(libs.serializationJson)
}
