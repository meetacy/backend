plugins {
    id(Deps.Plugins.Configuration.Kotlin.Jvm)
}

dependencies {
    api(project(Deps.Projects.Notification.Database))
    api(project(Deps.Projects.Notification.Usecase))
    api(project(Deps.Projects.Notification.Endpoints))
    api(project(Deps.Projects.Notification.Types))
    api(project(Deps.Projects.Notification.DatabaseIntegrations))
    api(project(Deps.Projects.Notification.UsecaseIntegrations))
}
