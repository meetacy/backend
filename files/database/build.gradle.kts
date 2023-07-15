plugins {
    id(Deps.Plugins.Configuration.Kotlin.Jvm)
}

dependencies {
    api(project(Deps.Projects.Files.Types))
    api(project(Deps.Projects.Types))
    api(Deps.Libs.Exposed.Core)

    api(project(Deps.Projects.User.Database))
}
