plugins {
    id("backend-convention")
}

dependencies {
    api(project(Deps.Projects.Paging.Types))

    api(Deps.Libs.Exposed.Core)
}
