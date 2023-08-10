plugins {
    id("backend-convention")
}

dependencies {
    api(projects.core.integration)
    api(projects.core.types.serializable.integration)
    api(projects.libs.paging.serializable.integration)
}
