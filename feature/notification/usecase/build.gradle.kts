plugins {
    id("backend-convention")
}

dependencies {
    api(project(Deps.Projects.Notification.Types))

    api(project(Deps.Projects.User.Usecase))
    api(project(Deps.Projects.Meetings.Usecase))
}
