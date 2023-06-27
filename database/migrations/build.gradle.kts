plugins {
    id(Deps.Plugins.Configuration.Kotlin.Jvm)
}

dependencies {
    api(project(Deps.Projects.Types))
    api(project(Deps.Projects.DatabaseInvitations))
    api(project(Deps.Projects.DatabaseFriends))
    api(project(Deps.Projects.DatabaseAuth))
    api(project(Deps.Projects.DatabaseEmail))
    api(project(Deps.Projects.DatabaseNotifications))
    api(project(Deps.Projects.DatabaseLocation))
    api(Deps.Libs.Meetacy.Wdater.Core)
    api(Deps.Libs.Meetacy.Wdater.AutoMigrations)
    api(Deps.Libs.Exposed.Core)
    implementation(Deps.Libs.Exposed.Jdbc)
    implementation(Deps.Libs.Postgres.Jdbc)
}
