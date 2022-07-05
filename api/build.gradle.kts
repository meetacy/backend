import org.gradle.util.GUtil.loadProperties

plugins {
    id(Deps.Plugins.Configuration.Kotlin.Jvm)
    id(Deps.Plugins.Serialization.Id)
    id(Deps.Plugins.Deploy.Id)
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

val service = "meetacy"

val propertiesFile = rootProject.file("deploy.properties")

deploy {
    if (propertiesFile.exists()) {
        ignore = false
        val properties = loadProperties(propertiesFile)

        host = properties.getProperty("host")
        user = properties.getProperty("user")
        password = properties.getProperty("password")
        deployPath = properties.getProperty("deployPath")
        knownHostsFile = properties.getProperty("knownHosts")

        mainClass = "app.meetacy.backend.MainKt"
        serviceName = service
    } else {
        ignore = true
    }
}

application {
    mainClass.set("app.meetacy.backend.MainKt")
}
