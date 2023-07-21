plugins {
    id("backend-convention")
}

dependencies {
    api(project(Deps.Projects.Email.Types))

    api(project(Deps.Projects.User.Usecase))
}
