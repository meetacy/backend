plugins {
    id("backend-convention")
}

dependencies {
    api(project(Deps.Projects.Auth.Database))
    api(project(Deps.Projects.Auth.Usecase))
    api(project(Deps.Projects.Auth.Types))

    api(Deps.Libs.Exposed.Core)
}
