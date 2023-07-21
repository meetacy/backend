plugins {
    id("backend-convention")
}

dependencies {
    api(project(Deps.Projects.Utf8Checker))
    api(project(Deps.Projects.UsecaseUtf8Checker.Root))
}