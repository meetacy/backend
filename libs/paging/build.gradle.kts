plugins {
    id("backend-convention")
}

dependencies {
    api(project(Deps.Projects.Paging.Types))
    api(project(Deps.Projects.Paging.Database))
}
