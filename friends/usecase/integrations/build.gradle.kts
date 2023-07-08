plugins {
    id(Deps.Plugins.Configuration.Kotlin.Jvm)
}

// usecase and endpoints dependencies
dependencies {
    api(project(Deps.Projects.Friends.Usecase))
    api(project(Deps.Projects.Usecase))
    api(project(Deps.Projects.Friends.Endpoints))
    api(project(Deps.Projects.UsecaseEndpoints))
}
