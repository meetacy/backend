plugins {
    id("backend-convention")
}

dependencies {
    api(projects.core.integration)
    api(projects.core.types.serializable.integration)
    api(projects.libs.paging.serializable.integration)
    api(libs.ktor.server.core)
    api(libs.meetacy.di.core)
    api(libs.meetacy.di.global)
}
