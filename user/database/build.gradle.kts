plugins {
    id(Deps.Plugins.Configuration.Kotlin.Jvm)
}

dependencies {
    api(project(Deps.Projects.User.Types))
    api(project(Deps.Projects.Types))
    api(project(Deps.Projects.Database))
    api(project(Deps.Projects.Files.Database))
    api(project(Deps.Projects.Auth.Types))

    implementation(Deps.Libs.Exposed.Core)
}
