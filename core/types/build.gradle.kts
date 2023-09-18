plugins {
    id("backend-convention")
}

dependencies {
    api(projects.core.constants)
    api(projects.libs.stdlibExtensions)
    api(libs.kotlinx.coroutines.core)
}
