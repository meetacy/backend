plugins {
    id(Deps.Plugins.Configuration.Kotlin.Jvm)
    id(Deps.Plugins.Serialization.Id)
}

dependencies {
    api(project(Deps.Projects.TypesSerialization))

    api(Deps.Libs.Ktor.Server.Core)
    api(Deps.Libs.Ktor.Server.Cio)
    api(Deps.Libs.Ktor.Client.Core)
    api(Deps.Libs.Ktor.Client.Cio)
    api(Deps.Libs.Ktor.Netty)
    api(Deps.Libs.Ktor.Ahr)
    api(Deps.Libs.Ktor.PartialContent)
    api(Deps.Libs.Ktor.Server.Testing)
    api(Deps.Plugins.Kotlin.Test)
    implementation(Deps.Libs.Ktor.Server.SerializationJson)
    implementation(Deps.Libs.Ktor.Server.ContentNegotiation)
    implementation(Deps.Libs.Kotlinx.Serialization)
    implementation(Deps.Libs.Slf4j.Simple)
    implementation("io.ktor:ktor-server-auto-head-response-jvm:2.1.1")
    implementation("io.ktor:ktor-server-partial-content-jvm:2.1.1")
    testImplementation("io.ktor:ktor-server-tests-jvm:2.1.1")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:1.7.10")
}
