plugins {
    id("backend-convention")
}

dependencies {
    api(projects.feature.common)

    api(projects.feature.email.types)
    api(projects.feature.user.usecase)
}
