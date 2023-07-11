plugins {
    id(Deps.Plugins.Configuration.Kotlin.Jvm)
}

// usecase and endpoints dependencies
dependencies {
    api(project(Deps.Projects.Email.Usecase))
    api(project(Deps.Projects.Usecase))
    api(project(Deps.Projects.Auth.Endpoints))
    api(project(Deps.Projects.Email.Types))
    api(project(Deps.Projects.UsecaseEndpoints))
}
