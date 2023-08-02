plugins {
    id("backend-convention")
    id(Deps.Plugins.Serialization.Id)
}

dependencies {
    api(projects.feature.invitations.types)
    api(projects.libs.ktorExtensions)
    api(projects.feature.user.types)
    api(projects.feature.meetings.endpoints)

    implementation(libs.kotlinx.serialization.json)
}
