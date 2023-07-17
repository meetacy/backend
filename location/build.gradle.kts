plugins {
    id(Deps.Plugins.Configuration.Kotlin.Jvm)
    id(Deps.Plugins.Serialization.Id)
}

dependencies {
    api(project(Deps.Projects.DateTime))

    implementation(Deps.Libs.Kotlinx.Serialization)
}
