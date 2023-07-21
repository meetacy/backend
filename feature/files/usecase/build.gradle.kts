plugins {
    id("backend-convention")
}

dependencies {
    api(project(Deps.Projects.Files.Types))

    api(project(Deps.Projects.Auth.Usecase))
}
