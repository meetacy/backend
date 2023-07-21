plugins {
    id("backend-convention")
}

dependencies {
    api(project(Deps.Projects.Notification.Usecase))
    api(project(Deps.Projects.Notification.Database))
    api(project(Deps.Projects.Updates.Usecase))
}
