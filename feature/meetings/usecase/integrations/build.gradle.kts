plugins {
    id("backend-convention")
}

// usecase and endpoints dependencies
dependencies {
    api(project(Deps.Projects.Meetings.Usecase))
    api(project(Deps.Projects.Meetings.Endpoints))
    api(project(Deps.Projects.Meetings.Types))

    api(project(Deps.Projects.User.UsecaseIntegrations))
}
