plugins {
    id("backend-convention")
}

dependencies {
    implementation(libs.meetacy.di.core)
    implementation(projects.feature.auth.usecase.integration)
    implementation(projects.feature.email.usecase.integration)
    implementation(projects.feature.files.usecase.integration)
    implementation(projects.feature.meetings.usecase.integration)
}
