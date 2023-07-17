plugins {
    id(Deps.Plugins.Configuration.Kotlin.Jvm)
    id(Deps.Plugins.Serialization.Id)
}

dependencies {
    implementation(Deps.Libs.Kotlinx.Serialization)
    api(project(Deps.Projects.Types)) // TODO: remove
}
