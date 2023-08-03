plugins {
    id("backend-convention")
}

dependencies {
    api(projects.application.usecase.database)
}
