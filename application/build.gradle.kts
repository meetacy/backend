import deploy.default
import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jetbrains.kotlin.konan.properties.loadProperties

plugins {
    application
    id("backend-convention")
    id("deploy-convention")
}

application {
    mainClass = "app.meetacy.backend.MainKt"
}

dependencies {

    implementation(projects.feature.common.types.integration)



    implementation(libs.mdi)
    implementation(projects.feature.common)
    implementation(projects.libs.utf8Checker.usecaseIntegration)
    implementation(project(Deps.Projects.Migrations))
    implementation(project(Deps.Projects.EndpointsNew))
    implementation(project(Deps.Projects.Invitation.Root))
    implementation(project(Deps.Projects.Notification.Root))
    implementation(project(Deps.Projects.Friends.Root))
    implementation(project(Deps.Projects.Updates.Root))
    implementation(project(Deps.Projects.Files.Root))
    implementation(project(Deps.Projects.Meetings.Root))
    implementation(project(Deps.Projects.User.Root))
    implementation(project(Deps.Projects.Auth.Root))
    implementation(project(Deps.Projects.Email.Root))
    implementation(project(Deps.Projects.DiscordWebhookKtor))

    testImplementation(Deps.Libs.Meetacy.Sdk.ApiKtor)
    testImplementation(libs.coroutinesTest)
    testImplementation(Deps.Libs.Ktor.Client.Core)
    testImplementation(Deps.Libs.Ktor.Client.Cio)
    testImplementation(Deps.Libs.Ktor.Client.Logging)
    testImplementation(Deps.Libs.Ktor.Client.Mock)
    testImplementation(Deps.Libs.Ktor.Client.Cio)
    testImplementation(libs.serializationJson)
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
    testLogging {
        exceptionFormat = TestExceptionFormat.FULL
    }
}

val propertiesFile: File = rootProject.file("deploy.properties")

deploy {
    val isRunner = System.getenv("IS_RUNNER")?.toBoolean() == true
    val properties = if (propertiesFile.exists()) loadProperties(propertiesFile.absolutePath) else null

    if (!isRunner && properties == null) return@deploy

    default {
        host = properties?.getProperty("host") ?: System.getenv("SSH_HOST")
        user = properties?.getProperty("user") ?: System.getenv("SSH_USER")
        password = properties?.getProperty("password") ?: System.getenv("SSH_PASSWORD")
        knownHostsFile = properties?.getProperty("knownHosts") ?: System.getenv("SSH_KNOWN_HOST_FILE")
        archiveName = "app.jar"

        mainClass = "app.meetacy.backend.MainKt"
    }

    target("production") {
        destination = properties?.getProperty("prod.destination") ?: System.getenv("DEPLOY_DESTINATION")
        serviceName = properties?.getProperty("prod.serviceName") ?: System.getenv("DEPLOY_SERVICE_NAME")
    }
}

application {
    mainClass.set("app.meetacy.backend.MainKt")
}

tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions.freeCompilerArgs += "-opt-in=kotlinx.serialization.ExperimentalSerializationApi"
}
