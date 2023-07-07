plugins {
    id(Deps.Plugins.Configuration.Kotlin.Jvm)
}

// usecase and endpoints dependencies
dependencies {
    api(project(Deps.Projects.Invitation.Usecase))
    api(project(Deps.Projects.Usecase))
    api(project(Deps.Projects.Invitation.Endpoints))
    api(project(Deps.Projects.Invitation.Types))
    api(project(Deps.Projects.UsecaseEndpoints))
}
