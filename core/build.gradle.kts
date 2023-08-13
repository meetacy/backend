plugins {
    id("backend-convention")
}

dependencies {
    api(projects.core.types)
    api(projects.core.types.integration)
    api(projects.core.integration)
    api(libs.meetacy.di.core)
}
