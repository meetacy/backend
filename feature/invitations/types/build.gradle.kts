plugins {
    id("backend-convention")
    id("serialization-convention")
}

dependencies {
    implementation(libs.kotlinx.serialization.json)
}
