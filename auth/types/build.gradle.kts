plugins {
    id(Deps.Plugins.Configuration.Kotlin.Jvm)
    id(Deps.Plugins.Serialization.Id)
}

dependencies {
    api(project(Deps.Projects.Types))
    api(project(Deps.Projects.Constants))

    implementation(Deps.Libs.Kotlinx.Serialization)
}
