plugins {
    id("backend-convention")
}

dependencies {
    api(project(Deps.Projects.User.Usecase))
    api(project(Deps.Projects.StdlibExtensions))
    api(project(Deps.Projects.Paging.Types))
}
