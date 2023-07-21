plugins {
    id("backend-convention")
}

dependencies {
    api(project(Deps.Projects.Auth.Database))
    api(project(Deps.Projects.Auth.Usecase))
    api(project(Deps.Projects.Auth.Endpoints))
    api(project(Deps.Projects.Auth.Types))
    api(project(Deps.Projects.Auth.DatabaseIntegrations))
    api(project(Deps.Projects.Auth.UsecaseIntegrations))
}
