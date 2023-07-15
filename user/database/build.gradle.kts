plugins {
    id(Deps.Plugins.Configuration.Kotlin.Jvm)
}

dependencies {
    api(project(Deps.Projects.User.Types))
    api(project(Deps.Projects.Types))
    api(project(Deps.Projects.Auth.Types))
    api(project(Deps.Projects.Files.Types))

    implementation(Deps.Libs.Exposed.Core)
}
