plugins {
    id(Deps.Plugins.Configuration.Kotlin.Jvm)
}

// usecase and endpoints dependencies
dependencies {
    api(project(Deps.Projects.Updates.Usecase))
    api(project(Deps.Projects.Usecase))
    api(project(Deps.Projects.Updates.Endpoints))
    api(project(Deps.Projects.Updates.Types))
    api(project(Deps.Projects.UsecaseEndpoints))
}
