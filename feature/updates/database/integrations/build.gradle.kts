plugins {
    id("backend-convention")
}

dependencies {
    api(project(Deps.Projects.Updates.Usecase))
    api(project(Deps.Projects.Updates.Database))
}
