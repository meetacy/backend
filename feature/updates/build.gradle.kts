plugins {
    id("backend-convention")
}

dependencies {
    api(project(Deps.Projects.Updates.Database))
    api(project(Deps.Projects.Updates.Usecase))
    api(project(Deps.Projects.Updates.Endpoints))
    api(project(Deps.Projects.Updates.Types))
    api(project(Deps.Projects.Updates.DatabaseIntegrations))
    api(project(Deps.Projects.Updates.UsecaseIntegrations))
}
