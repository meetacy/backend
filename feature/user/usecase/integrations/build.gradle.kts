plugins {
    id("backend-convention")
}

// usecase and endpoints dependencies
dependencies {
    api(project(Deps.Projects.User.Usecase))
    api(project(Deps.Projects.User.Endpoints))
    api(project(Deps.Projects.User.Types))

    api(project(Deps.Projects.Files.Usecase))
}
