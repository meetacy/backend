plugins {
    id(Deps.Plugins.Configuration.Kotlin.Jvm)
}

dependencies {
    api(project(Deps.Projects.Updates.Usecase))
    api(project(Deps.Projects.Updates.Database))
    api(project(Deps.Projects.DatabaseUsecase))
}
