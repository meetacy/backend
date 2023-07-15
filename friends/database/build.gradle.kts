plugins {
    id(Deps.Plugins.Configuration.Kotlin.Jvm)
}

dependencies {
    api(project(Deps.Projects.Types))
    api(project(Deps.Projects.User.Database))
    api(project(Deps.Projects.Paging.it))

    implementation(Deps.Libs.Exposed.Core)
}
