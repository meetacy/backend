plugins {
    id(Deps.Plugins.Configuration.Kotlin.Jvm)
}

dependencies {
    api(Deps.Libs.Exposed.Core)
    api(project(Deps.Projects.DatabaseMigrations))
    api(project(Deps.Projects.DatabaseFriendsIntegrations))
    api(project(Deps.Projects.DatabaseInvitationsIntegration))
    api(project(Deps.Projects.DatabaseTypesIntegrations))
    api(project(Deps.Projects.DatabaseUsersIntegrations))
    api(project(Deps.Projects.DatabaseMeetingsIntegrations))
    api(project(Deps.Projects.DatabaseFilesIntegrations))
    api(project(Deps.Projects.DatabaseAuthIntegrations))
    api(project(Deps.Projects.DatabaseEmailIntegrations))
    api(project(Deps.Projects.DatabaseNotificationsIntegrations))
    api(project(Deps.Projects.DatabaseLocationIntegrations))
}
