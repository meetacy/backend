plugins {
    id("backend-convention")
}

dependencies {
    api(project(Deps.Projects.Email.Types))
    api(Deps.Libs.Exposed.Core)

    api(project(Deps.Projects.User.Database))
}
