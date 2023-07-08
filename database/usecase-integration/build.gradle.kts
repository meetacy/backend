plugins {
    id(Deps.Plugins.Configuration.Kotlin.Jvm)
}

dependencies {
    api(project(Deps.Projects.Usecase))
    api(project(Deps.Projects.Database))
    api(project(Deps.Projects.Friends.Database))
}
