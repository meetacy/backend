plugins {
    id("backend-convention")
}

dependencies {
    api(project(Deps.Projects.Meetings.Types))

    api(project(Deps.Projects.Files.Usecase))
    api(project(Deps.Projects.User.Usecase))
    api(project(Deps.Projects.Paging.Types))

    implementation(libs.coroutines)
    api(project(Deps.Projects.StdlibExtensions))
}
