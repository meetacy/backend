plugins {
    id(Deps.Plugins.Configuration.Kotlin.Jvm)
    id(Deps.Plugins.Serialization.Id)
}

dependencies {
    implementation(Deps.Libs.Ktor.Server.Core)
    implementation(Deps.Libs.Ktor.Server.Cio)
    implementation(Deps.Libs.Ktor.Server.SerializationJson)
    implementation(Deps.Libs.Ktor.Server.ContentNegotiation)
    implementation(Deps.Libs.Kotlinx.Serialization)
    implementation(Deps.Libs.Slf4j.Simple)

    testImplementation(Deps.Libs.Ktor.Client.Core)
    testImplementation(Deps.Libs.Ktor.Client.Cio)
    testImplementation(Deps.Libs.Ktor.Client.SerializationJson)
    testImplementation(Deps.Libs.Ktor.Client.ContentNegotiation)
}
