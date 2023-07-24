plugins {
    id("backend-convention")
}

dependencies {
    api(project(Deps.Projects.Notification.Types))
    implementation(Deps.Libs.Exposed.Core)

    api(project(Deps.Projects.User.Database))
    api(project(Deps.Projects.Meetings.Database))

    api(projects.libs.paging)
}
