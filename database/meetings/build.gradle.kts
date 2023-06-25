plugins {
    id(Deps.Plugins.Configuration.Kotlin.Jvm)
}

dependencies {
    api(project(Deps.Projects.Types))
    api(project(Deps.Projects.DatabaseTypes))
    api(project(Deps.Projects.DatabaseUsers))
    api(project(Deps.Projects.DatabaseFiles))
    api(project(Deps.Projects.DatabaseTransactions))
    api(Deps.Libs.Exposed.Core)
    implementation(Deps.Libs.Exposed.Jdbc)
    implementation(Deps.Libs.Postgres.Jdbc)
}
