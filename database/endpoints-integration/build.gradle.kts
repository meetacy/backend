plugins {
    id(Deps.Plugins.Configuration.Kotlin.Jvm)
}

dependencies {
    api(project(Deps.Projects.Endpoints))
    api(project(Deps.Projects.Database))
}
