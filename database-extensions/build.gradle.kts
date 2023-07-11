plugins {
    id(Deps.Plugins.Configuration.Kotlin.Jvm)
}

dependencies {
    api(project(Deps.Projects.Types))
    api(project(Deps.Projects.Database))
    api(project(Deps.Projects.Invitation.Database))
    api(project(Deps.Projects.Notification.Database))
    api(project(Deps.Projects.Friends.Database))
    api(project(Deps.Projects.Updates.Database))
    api(project(Deps.Projects.Files.Database))
    api(project(Deps.Projects.Meetings.Database))
    api(project(Deps.Projects.User.Database))
    api(project(Deps.Projects.Auth.Database))
    api(project(Deps.Projects.Email.Database))
    
    api(Deps.Libs.Meetacy.Wdater.Core)
    api(Deps.Libs.Meetacy.Wdater.AutoMigrations)
    api(Deps.Libs.Exposed.Core)
    implementation(Deps.Libs.Exposed.Jdbc)
    implementation(Deps.Libs.Postgres.Jdbc)
}
