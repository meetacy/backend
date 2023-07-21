plugins {
    id(Deps.Plugins.Configuration.Kotlin.Jvm)
}

// usecase and endpoints dependencies
dependencies {
    api(project(Deps.Projects.Notification.Usecase))
    api(project(Deps.Projects.Notification.Endpoints))
    api(project(Deps.Projects.Notification.Types))

    api(project(Deps.Projects.Meetings.UsecaseIntegrations))
    api(project(Deps.Projects.User.UsecaseIntegrations))
}
