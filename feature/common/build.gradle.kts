plugins {
    id("backend-convention")
}

dependencies {
    api(projects.feature.common.types)
    api(projects.feature.common.types.integration)
    api(projects.feature.common.integration)
}