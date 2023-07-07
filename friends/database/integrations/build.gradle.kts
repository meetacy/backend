plugins {
    id(Deps.Plugins.Configuration.Kotlin.Jvm)
}

dependencies {
    api(project(Deps.Projects.Friends.Usecase))
    api(project(Deps.Projects.Friends.Database))
    api(project(Deps.Projects.DatabaseUsecase))
}
