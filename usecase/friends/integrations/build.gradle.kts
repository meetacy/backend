plugins {
    id(Deps.Plugins.Configuration.Kotlin.Jvm)
}

dependencies {
    api(project(Deps.Projects.UsecaseFriends))
    api(project(Deps.Projects.UsecaseLocation))
    api(project(Deps.Projects.UsecaseEndpoints))
    api(project(Deps.Projects.Endpoints))
}
