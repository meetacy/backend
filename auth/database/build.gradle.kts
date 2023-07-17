plugins {
    id(Deps.Plugins.Configuration.Kotlin.Jvm)
}

dependencies {
    api(project(Deps.Projects.Auth.Types))
    implementation(Deps.Libs.Exposed.Core)
}
