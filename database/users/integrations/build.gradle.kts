plugins {
    id(Deps.Plugins.Configuration.Kotlin.Jvm)
}

dependencies {
    api(project(Deps.Projects.UsecaseAuth))
    api(project(Deps.Projects.DatabaseUsers))
    api(project(Deps.Projects.DatabaseTypesIntegrations))
    api(project(Deps.Projects.DatabaseFriends))
}
