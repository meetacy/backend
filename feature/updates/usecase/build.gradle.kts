plugins {
    id(Deps.Plugins.Configuration.Kotlin.Jvm)
}

dependencies {
    api(project(Deps.Projects.Updates.Types))
    api(project(Deps.Projects.Notification.Usecase))
}
