plugins {
    id("backend-convention")
}

dependencies {
    implementation(projects.core.usecase.integration)
    implementation(projects.feature.search.usecase)
    implementation(projects.feature.meetings.database)
    implementation(projects.feature.users.database)
    implementation(projects.libs.googleMaps.ktor)
}
