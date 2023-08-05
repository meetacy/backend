plugins {
    id("backend-convention")
}

dependencies {

    api(projects.feature.auth.usecase)
}
