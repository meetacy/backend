plugins {
    id(Deps.Plugins.Configuration.Kotlin.Jvm)
}

dependencies {
    api(project(Deps.Projects.Types))
    api(project(Deps.Projects.Database))
    api(project(Deps.Projects.User.Database))

    implementation(Deps.Libs.Exposed.Core)
}
