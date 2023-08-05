plugins {
    id("backend-convention")
    id(Deps.Plugins.Serialization.Id)
}

dependencies {
    api(projects.core.typesSerializable)

    api(projects.libs.ktorExtensions)

    api(projects.libs.paging.types)

    implementation(libs.kotlinx.serialization.json)
}
