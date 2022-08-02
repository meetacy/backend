plugins {
    id(Deps.Plugins.Configuration.Kotlin.Jvm)
}

dependencies {
    api(project(Deps.Projects.HashGenerator))
    api(project(Deps.Projects.Usecase))
}
