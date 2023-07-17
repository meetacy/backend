plugins {
    id(Deps.Plugins.Configuration.Kotlin.Jvm)
}

dependencies {
    api(project(Deps.Projects.Email.Types))

    api(project(Deps.Projects.User.Usecase))
}
