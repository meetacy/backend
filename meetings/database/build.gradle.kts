plugins {
    id(Deps.Plugins.Configuration.Kotlin.Jvm)
}

dependencies {
    api(project(Deps.Projects.Meetings.Types))
    api(project(Deps.Projects.Types))
    api(project(Deps.Projects.Database))
    implementation(Deps.Libs.Exposed.Core)

    api(project(Deps.Projects.Paging.it))
    
    api(project(Deps.Projects.Files.Database))
}
