plugins {
    id("backend-convention")
}

// usecase and endpoints dependencies
dependencies {
    api(project(Deps.Projects.Updates.Usecase))
    api(project(Deps.Projects.Updates.Endpoints))
    api(project(Deps.Projects.Updates.Types))
    api(project(Deps.Projects.Notification.UsecaseIntegrations))
}
