plugins {
    id("backend-convention")
}

dependencies {
    api(project(Deps.Projects.Meetings.Types))
    implementation(Deps.Libs.Exposed.Core)

    api(project(Deps.Projects.Paging.Root))
    api(project(Deps.Projects.ExposedExtensions))
    
    api(project(Deps.Projects.Files.Database))
}
