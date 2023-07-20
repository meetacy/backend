plugins {
    id(Deps.Plugins.Configuration.Kotlin.Jvm)
}

dependencies {
    api(project(Deps.Projects.Invitation.Types))
    implementation(Deps.Libs.Exposed.Core)

    api(project(Deps.Projects.Meetings.Database))
    api(project(Deps.Projects.User.Database))
}
