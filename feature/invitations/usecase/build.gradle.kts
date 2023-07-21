plugins {
    id("backend-convention")
}

dependencies {
    api(project(Deps.Projects.Invitation.Types))

    api(project(Deps.Projects.Meetings.Usecase))
}
