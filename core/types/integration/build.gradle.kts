plugins {
    id("backend-convention")
}

dependencies {
//    api(projects.constants)
    api(projects.libs.hashGenerator)
    api(projects.libs.utf8Checker)
    api(projects.core.types)
    implementation(libs.meetacy.di.global)
    implementation(libs.meetacy.di.core)
}
