plugins {
    id("backend-convention")
    id(Deps.Plugins.Serialization.Id)
}

dependencies {
    api(projects.feature.email.types)
    api(projects.libs.ktorExtensions)

    implementation(libs.ktor.server.core)
    implementation(libs.kotlinx.serialization.json)
}
