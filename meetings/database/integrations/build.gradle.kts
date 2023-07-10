plugins {
    id(Deps.Plugins.Configuration.Kotlin.Jvm)
}

dependencies {
    api(project(Deps.Projects.Meetings.Usecase))
    api(project(Deps.Projects.Meetings.Database))
    api(project(Deps.Projects.Files.DatabaseIntegrations))
    api(project(Deps.Projects.DatabaseUsecase))
}
