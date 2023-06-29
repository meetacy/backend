plugins {
    id(Deps.Plugins.Configuration.Kotlin.Jvm)
}

dependencies {
    api(project(Deps.Projects.UsecaseFiles))
    api(project(Deps.Projects.UsecaseEndpoints))
    api(project(Deps.Projects.Endpoints))
}
