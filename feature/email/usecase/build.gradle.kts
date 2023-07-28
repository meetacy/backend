plugins {
    id("backend-convention")
}

dependencies {
    api(projects.core)

    api(projects.feature.email.types)
    api(projects.feature.user.usecase)
}
