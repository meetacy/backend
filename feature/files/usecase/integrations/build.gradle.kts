plugins {
    id("backend-convention")
}

// usecase and endpoints dependencies
dependencies {
    api(project(Deps.Projects.Files.Usecase))
    api(project(Deps.Projects.Files.Endpoints))
    api(project(Deps.Projects.Files.Types))
}
