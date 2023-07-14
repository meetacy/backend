plugins {
    id(Deps.Plugins.Configuration.Kotlin.Jvm)
}

dependencies {
    api(project(Deps.Projects.Meetings.Types))
    api(project(Deps.Projects.Types))

    api(project(Deps.Projects.Files.Usecase))
    api(project(Deps.Projects.User.Usecase))

    implementation(Deps.Libs.Kotlinx.Coroutines)
    api(project(Deps.Projects.StdlibExtensions))
}
