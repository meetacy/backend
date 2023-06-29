plugins {
    id(Deps.Plugins.Configuration.Kotlin.Jvm)
}

dependencies {
    api(Deps.Libs.Kotlinx.Coroutines)
    api(project(Deps.Projects.StdlibExtensions))
    api(project(Deps.Projects.Types))
    api(project(Deps.Projects.Usecase))
}
