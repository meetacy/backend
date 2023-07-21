plugins {
    id("backend-convention")
}

dependencies {
    api(project(Deps.Projects.User.Types))

    api(project(Deps.Projects.Files.Usecase))
    api(project(Deps.Projects.Auth.Usecase))
}
