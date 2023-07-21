plugins {
    id("backend-convention")
    id("serialization-convention")
}

dependencies {
    api(projects.amount)

    implementation(libs.serializationJson)
}
