plugins {
    id("backend-convention")
}

// usecase and endpoints dependencies
dependencies {
    api(project(Deps.Projects.Email.Usecase))
    api(project(Deps.Projects.Auth.Endpoints))
    api(project(Deps.Projects.Email.Types))
}
