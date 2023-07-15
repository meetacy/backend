plugins {
    id(Deps.Plugins.Configuration.Kotlin.Jvm)
}

dependencies {
    api(project(Deps.Projects.Updates.Types))
    api(project(Deps.Projects.Types))
    api(project(Deps.Projects.Database))
    implementation(Deps.Libs.Exposed.Core)

    api(project(Deps.Projects.User.Database))
    api(project(Deps.Projects.ExposedExtensions))
}
