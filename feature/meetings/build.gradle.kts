plugins {
    id("backend-convention")
}

dependencies {
    api(project(Deps.Projects.Meetings.Database))
    api(project(Deps.Projects.Meetings.Usecase))
    api(project(Deps.Projects.Meetings.Endpoints))
    api(project(Deps.Projects.Meetings.Types))
    api(project(Deps.Projects.Meetings.DatabaseIntegrations))
    api(project(Deps.Projects.Meetings.UsecaseIntegrations))

}
