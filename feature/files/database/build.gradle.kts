plugins {
    id("backend-convention")
}

dependencies {
    api(project(Deps.Projects.Files.Types))
    api(Deps.Libs.Exposed.Core)

    api(project(Deps.Projects.User.Database))
}
