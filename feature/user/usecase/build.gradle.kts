plugins {
    id("backend-convention")
}

dependencies {
    api(projects.feature.user.types)
    api(projects.feature.files.usecase)
    api(projects.feature.auth.usecase)
}
