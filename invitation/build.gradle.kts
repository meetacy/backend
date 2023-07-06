plugins {
    id(Deps.Plugins.Configuration.Kotlin.Jvm)
}

dependencies {
    api(project(Deps.Projects.Invitation.DatabaseIntegrations))
    api(project(Deps.Projects.Invitation.UsecaseIntegrations))
    api(project(Deps.Projects.Invitation.TypesIntegrations))
}
