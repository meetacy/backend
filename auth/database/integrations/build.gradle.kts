plugins {
    id(Deps.Plugins.Configuration.Kotlin.Jvm)
}

dependencies {
    api(project(Deps.Projects.Auth.Database))
    api(project(Deps.Projects.Auth.Usecase))
    api(project(Deps.Projects.Auth.Types))
}
