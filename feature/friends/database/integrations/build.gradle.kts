plugins {
    id("backend-convention")
}

dependencies {
    api(project(Deps.Projects.Friends.Usecase))
    api(project(Deps.Projects.Friends.Database))
    api(project(Deps.Projects.Notification.Usecase))

    api(project(Deps.Projects.User.DatabaseIntegrations))
}
