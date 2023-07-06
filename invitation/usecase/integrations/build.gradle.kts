plugins {
    id(Deps.Plugins.Configuration.Kotlin.Jvm)
}

// usecase and endpoints dependencies
dependencies {
    implementation(project(Deps.Projects.Usecase))
    implementation(project(Deps.Projects.Endpoints))
}
