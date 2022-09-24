plugins {
    id(Deps.Plugins.Configuration.Kotlin.Jvm)
    id(Deps.Plugins.Serialization.Id)
}

dependencies {
    implementation(Deps.Libs.Kotlinx.Serialization)
    api(Deps.Libs.Ktor.PartialContent)
    api(Deps.Libs.Ktor.Server.Core)
    api(Deps.Libs.Ktor.Server.Cio)
}
