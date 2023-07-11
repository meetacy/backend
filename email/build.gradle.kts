plugins {
    id(Deps.Plugins.Configuration.Kotlin.Jvm)
}

dependencies {
    api(project(Deps.Projects.Email.Database))
    api(project(Deps.Projects.Email.Usecase))
    api(project(Deps.Projects.Email.Endpoints))
    api(project(Deps.Projects.Email.Types))
    api(project(Deps.Projects.Email.DatabaseIntegrations))
    api(project(Deps.Projects.Email.UsecaseIntegrations))
}
