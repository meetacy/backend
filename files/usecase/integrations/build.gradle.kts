plugins {
    id(Deps.Plugins.Configuration.Kotlin.Jvm)
}

// usecase and endpoints dependencies
dependencies {
    api(project(Deps.Projects.Files.Usecase))
    api(project(Deps.Projects.Files.Endpoints))
    api(project(Deps.Projects.Files.Types))
}
