plugins {
    id("backend-convention")
    id("serialization-convention")
}

dependencies {
//    api(project(Deps.Projects.Auth.Types))
//    api(project(Deps.Projects.LocationTypes))
//    api(project(Deps.Projects.AmountTypes))
//    api(project(Deps.Projects.Optional))
    api(projects.feature.auth.types)
    api(projects.location)

    implementation(libs.serializationGradle)
}
