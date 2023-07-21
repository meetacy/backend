plugins {
    id(Deps.Plugins.Configuration.Kotlin.Jvm)
}

dependencies {
    api(project(Deps.Projects.Updates.Types))
    implementation(Deps.Libs.Exposed.Core)

    api(project(Deps.Projects.User.Database))
    api(project(Deps.Projects.ExposedExtensions))
}
