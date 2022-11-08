import org.gradle.util.GUtil.loadProperties

plugins {
    id(Deps.Plugins.Configuration.Kotlin.Jvm)
    id(Deps.Plugins.Deploy.Id)
}

dependencies {
    implementation(project(Deps.Projects.Endpoints))
    implementation(project(Deps.Projects.UsecaseEndpoints))
    implementation(project(Deps.Projects.DatabaseUsecase))
    implementation(project(Deps.Projects.DatabaseEndpoints))
    implementation(project(Deps.Projects.HashGeneratorUsecase))
}

val propertiesFile = rootProject.file("deploy.properties")

deploy {
    if (propertiesFile.exists()) {
        val properties = loadProperties(propertiesFile)

        default {
            host = properties.getProperty("host") ?: System.getenv("SSH_HOST")
            user = properties.getProperty("user") ?: System.getenv("SSH_USER")
            password = properties.getProperty("password") ?: System.getenv("SSH_PASSWORD")
            knownHostsFile = properties.getProperty("knownHosts") ?: System.getenv("SSH_KNOWN_HOST_FILE")
            archiveName = "app.jar"

            mainClass = "app.meetacy.backend.MainKt"
        }

        target("production") {
            destination = properties.getProperty("prod.destination") ?: System.getenv("DEPLOY_DESTINATION")
            serviceName = properties.getProperty("prod.serviceName") ?: System.getenv("DEPLOY_SERVICE_NAME")
        }
    }
}

application {
    mainClass.set("app.meetacy.backend.MainKt")
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    kotlinOptions.freeCompilerArgs += "-opt-in=kotlinx.serialization.ExperimentalSerializationApi"
}
