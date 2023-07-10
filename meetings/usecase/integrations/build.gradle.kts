plugins {
    id(Deps.Plugins.Configuration.Kotlin.Jvm)
}

// usecase and endpoints dependencies
dependencies {
    api(project(Deps.Projects.Meetings.Usecase))
    api(project(Deps.Projects.Usecase))
    api(project(Deps.Projects.Meetings.Endpoints))
    api(project(Deps.Projects.Meetings.Types))

    api(project(Deps.Projects.UsecaseEndpoints))
}
