plugins {
    id(Deps.Plugins.Configuration.Kotlin.Jvm)
}

dependencies {
    api(project(Deps.Projects.Usecase))
    api(project(Deps.Projects.UsecaseInvitations))
    api(project(Deps.Projects.DatabaseInvitations))
    api(project(Deps.Projects.DatabaseTypesIntegrations))
    api(project(Deps.Projects.DatabaseFriends))
}
