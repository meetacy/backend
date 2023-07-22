plugins {
    id("backend-convention")
}

dependencies {
//    api(project(Deps.Projects.User.Usecase))
//    api(project(Deps.Projects.User.Database))
//    api(project(Deps.Projects.Friends.Database))
//
//    api(project(Deps.Projects.Files.DatabaseIntegrations))
//    api(project(Deps.Projects.Auth.Database))
    api(projects.feature.user.usecase)
    api(projects.feature.user.database)
    api(projects.feature.friends.database)

//    api(projects.feature.files.database.integrations) TODO
}
