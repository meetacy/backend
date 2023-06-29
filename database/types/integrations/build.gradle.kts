plugins {
    id(Deps.Plugins.Configuration.Kotlin.Jvm)
}

dependencies {
    api(project(Deps.Projects.Usecase))
    api(project(Deps.Projects.DatabaseInvitations))
    api(project(Deps.Projects.UsecaseNotification))
    api(project(Deps.Projects.DatabaseAuth))
}
