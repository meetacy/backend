plugins {
    id("backend-convention")
    id(Deps.Plugins.Serialization.Id)
}

dependencies {
    api(projects.libs.ktorExtensions)
    api(projects.feature.notification.endpoints)

    implementation(libs.ktor.server.core)
    implementation(libs.kotlinx.serialization.json)
}
