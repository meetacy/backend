plugins {
    id("backend-convention")
}

// usecase and endpoints dependencies
dependencies {
    api(projects.feature.files.usecase)
    api(projects.feature.files.endpoints)
    api(projects.feature.files.types)
}
