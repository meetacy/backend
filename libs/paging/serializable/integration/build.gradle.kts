plugins {
    id("backend-convention")
}

dependencies {
    implementation(projects.libs.paging)
    implementation(projects.libs.paging.serializable)
}
