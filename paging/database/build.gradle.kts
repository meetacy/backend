plugins {
    id(Deps.Plugins.Configuration.Kotlin.Jvm)
}

dependencies {
    api(project(Deps.Projects.Paging.Types))

    api(Deps.Libs.Exposed.Core)
}
