plugins {
    id("backend-convention")
}

dependencies {
    api(projects.core.constants)
    api(libs.kotlinx.coroutines.core)
}
