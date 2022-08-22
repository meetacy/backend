import org.gradle.util.GUtil.loadProperties

plugins {
    id(Deps.Plugins.Configuration.Kotlin.Jvm)
    id(Deps.Plugins.Deploy.Id)
}

dependencies {
    implementation(project(Deps.Projects.Endpoints))
    implementation(project(Deps.Projects.UsecaseEndpoints))
    implementation(project(Deps.Projects.MockUsecase))
    implementation(project(Deps.Projects.DatabaseUsecase))
    implementation(project(Deps.Projects.HashGeneratorUsecase))
}

val propertiesFile = rootProject.file("deploy.properties")

deploy {
    if (propertiesFile.exists()) {
        val properties = loadProperties(propertiesFile)

        default {
            host = properties.getProperty("host")
            user = properties.getProperty("user")
            password = properties.getProperty("password")
            knownHostsFile = properties.getProperty("knownHosts")
            archiveName = "app.jar"

            mainClass = "app.meetacy.backend.MainKt"
        }

        target("production") {
            destination = properties.getProperty("prod.destination")
            serviceName = properties.getProperty("prod.serviceName")
        }
    }
}

application {
    mainClass.set("app.meetacy.backend.MainKt")
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    kotlinOptions.freeCompilerArgs += "-opt-in=kotlinx.serialization.ExperimentalSerializationApi"
}
