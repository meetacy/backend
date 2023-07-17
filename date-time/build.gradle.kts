plugins {
    id(Deps.Plugins.Configuration.Kotlin.Jvm)
    id(Deps.Plugins.Serialization.Id)
}

dependencies {
    api(project(Deps.Projects.Annotations))

    implementation(Deps.Libs.Kotlinx.Serialization)
}
