plugins {
    id("backend-convention")
}

// usecase and endpoints dependencies
dependencies {
    api(project(Deps.Projects.Friends.Usecase))
    api(project(Deps.Projects.Friends.Endpoints))

    api(project(Deps.Projects.User.UsecaseIntegrations))
}
