plugins {
    id("backend-convention")
}

dependencies {
    api(project(Deps.Projects.User.Database))
    api(project(Deps.Projects.User.Usecase))
    api(project(Deps.Projects.User.Endpoints))
    api(project(Deps.Projects.User.Types))
    api(project(Deps.Projects.User.DatabaseIntegrations))
    api(project(Deps.Projects.User.UsecaseIntegrations))
}
