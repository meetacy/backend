plugins {
    id("backend-convention")
}

dependencies {
    api(project(Deps.Projects.User.Database))
    api(project(Deps.Projects.Paging.Root))

    implementation(Deps.Libs.Exposed.Core)
}
