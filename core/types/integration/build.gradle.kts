plugins {
    id("backend-convention")
}

dependencies {
//    api(projects.constants)
    api(projects.libs.hashGenerator)
    api(projects.core.types)
    implementation(libs.meetacy.di.global)
}
