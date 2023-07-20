plugins {
    id(Deps.Plugins.Configuration.Kotlin.Jvm)
}

dependencies {
    api(project(Deps.Projects.Invitation.Types))

    api(project(Deps.Projects.Meetings.Usecase))
}
