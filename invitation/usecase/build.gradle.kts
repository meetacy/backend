plugins {
    id(Deps.Plugins.Configuration.Kotlin.Jvm)
}

dependencies {
    api(project(Deps.Projects.Invitation.Types))
    api(project(Deps.Projects.Types))
    api(project(Deps.Projects.Usecase))

    api(project(Deps.Projects.Meetings.Usecase))
}
