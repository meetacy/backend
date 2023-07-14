plugins {
    id(Deps.Plugins.Configuration.Kotlin.Jvm)
}

dependencies {
    api(project(Deps.Projects.Utf8Checker))
    api(project(Deps.Projects.UsecaseUtf8Checker.it))
}