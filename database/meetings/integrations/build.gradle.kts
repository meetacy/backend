plugins {
    id(Deps.Plugins.Configuration.Kotlin.Jvm)
}

dependencies {
    api(project(Deps.Projects.Usecase))
    api(project(Deps.Projects.DatabaseMeetings))
    api(project(Deps.Projects.DatabaseUsersIntegrations))
    api(project(Deps.Projects.DatabaseUsecase))
}
