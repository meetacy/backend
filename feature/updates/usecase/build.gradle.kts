plugins {
    id("backend-convention")
}

dependencies {
    api(project(Deps.Projects.Updates.Types))
    api(project(Deps.Projects.Notification.Usecase))
}
