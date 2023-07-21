plugins {
    id(Deps.Plugins.Configuration.Kotlin.Jvm)
}

dependencies {
    api(project(Deps.Projects.Paging.Types))
    api(project(Deps.Projects.Paging.Database))
}
