plugins {
    id("backend-convention")
}

dependencies {
    api(project(Deps.Projects.Email.Usecase))
    api(project(Deps.Projects.Email.Database))
}
