plugins {
    id(Deps.Plugins.Configuration.Kotlin.Jvm)
}

dependencies {
    api(project(Deps.Projects.Types))
    api(project(Deps.Projects.DatabaseTypes))
    api(project(Deps.Projects.DatabaseUsers))
    api(project(Deps.Projects.DatabaseMeetings))
    api(Deps.Libs.Meetacy.Wdater.Core)
    api(Deps.Libs.Meetacy.Wdater.AutoMigrations)
    api(Deps.Libs.Exposed.Core)
    implementation(Deps.Libs.Exposed.Jdbc)
    implementation(Deps.Libs.Postgres.Jdbc)
}
