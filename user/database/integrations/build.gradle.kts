plugins {
    id(Deps.Plugins.Configuration.Kotlin.Jvm)
}

dependencies {
    api(project(Deps.Projects.User.Usecase))
    api(project(Deps.Projects.User.Database))
    api(project(Deps.Projects.DatabaseUsecase))

    api(project(Deps.Projects.Files.DatabaseIntegrations))
    api(project(Deps.Projects.Auth.Database))
}
