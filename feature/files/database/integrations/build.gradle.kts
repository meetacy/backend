plugins {
    id("backend-convention")
}

dependencies {
    api(project(Deps.Projects.Files.Usecase))
    api(project(Deps.Projects.Files.Database))
    api(project(Deps.Projects.Files.Endpoints))
}
