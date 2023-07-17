plugins {
    id(Deps.Plugins.Configuration.Kotlin.Jvm)
}

dependencies {
    api(project(Deps.Projects.Auth.Types))

    api(project(Deps.Projects.User.Types))
    api(project(Deps.Projects.UsecaseUtf8Checker.it))
    api(project(Deps.Projects.UsecaseHashGenerator.it))
}
