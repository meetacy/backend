plugins {
    id("backend-convention")
}

dependencies {
    api(project(Deps.Projects.Meetings.Usecase))
    api(project(Deps.Projects.Meetings.Database))
    api(project(Deps.Projects.Files.DatabaseIntegrations))
    api(project(Deps.Projects.User.DatabaseIntegrations))
}
