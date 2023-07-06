plugins {
    id(Deps.Plugins.Configuration.Kotlin.Jvm)
}

dependencies {
    api(project(Deps.Projects.Invitation.Usecase))
    api(project(Deps.Projects.Invitation.Database))
}
