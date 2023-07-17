plugins {
    id(Deps.Plugins.Configuration.Kotlin.Jvm)
}

dependencies {
    api(project(Deps.Projects.Annotations))
    implementation(project(Deps.Projects.DateTime)) // TODO: remove
}
