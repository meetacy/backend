plugins {
    id(Deps.Plugins.Configuration.Kotlin.Jvm)
    id(Deps.Plugins.Serialization.Id)
}

dependencies {
    api(project(Deps.Projects.Auth.Types))

    implementation(Deps.Libs.Kotlinx.Serialization)

}
