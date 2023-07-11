plugins {
    id(Deps.Plugins.Configuration.Kotlin.Jvm)
}

dependencies {
    api(project(Deps.Projects.Email.Usecase))
    api(project(Deps.Projects.Email.Database))
}
