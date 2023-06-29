plugins {
    id(Deps.Plugins.Configuration.Kotlin.Jvm)
}

dependencies {
    api(project(Deps.Projects.UsecaseEmail))
    api(project(Deps.Projects.UsecaseEndpoints))
    api(project(Deps.Projects.Endpoints))
}
