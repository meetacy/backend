plugins {
    id("backend-convention")
}

// usecase and endpoints dependencies
dependencies {
    api(projects.feature.email.usecase)
    api(projects.feature.auth.endpoints)
}
