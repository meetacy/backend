plugins {
    id("backend-convention")
}

dependencies {
    api(projects.feature.files.types)

    api(projects.feature.auth.usecase)
}
