plugins {
    id(Deps.Plugins.Configuration.Kotlin.Jvm)
}

dependencies {
    api(project(Deps.Projects.Files.Usecase))
    api(project(Deps.Projects.Files.Database))
    api(project(Deps.Projects.DatabaseUsecase))
    api(project(Deps.Projects.Updates.Usecase))
}
