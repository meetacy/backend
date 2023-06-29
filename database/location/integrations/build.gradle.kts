plugins {
    id(Deps.Plugins.Configuration.Kotlin.Jvm)
}

dependencies {
    api(project(Deps.Projects.UsecaseLocation))
    api(project(Deps.Projects.DatabaseLocation))
    api(project(Deps.Projects.DatabaseFriends))
}
