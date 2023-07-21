plugins {
    id("backend-convention")
}

dependencies {
    api(project(Deps.Projects.Friends.Database))
    api(project(Deps.Projects.Friends.Usecase))
    api(project(Deps.Projects.Friends.Endpoints))
    api(project(Deps.Projects.Friends.DatabaseIntegrations))
    api(project(Deps.Projects.Friends.UsecaseIntegrations))
}
