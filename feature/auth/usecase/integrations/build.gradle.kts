plugins {
    id("backend-convention")
}

// usecase and endpoints dependencies
dependencies {
    api(project(Deps.Projects.Auth.Usecase))
    api(project(Deps.Projects.Auth.Endpoints))
    api(project(Deps.Projects.Auth.Types))
}
