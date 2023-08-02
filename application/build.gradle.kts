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
    implementation(projects.core.types.integration)

    // features
    implementation(projects.feature.auth)
    implementation(projects.core)
    implementation(projects.feature.email)
    implementation(projects.feature.files)
    implementation(projects.feature.friends)
    implementation(projects.feature.invitations)
    implementation(projects.feature.notification)
    implementation(projects.feature.meetings)
    implementation(projects.feature.updates)
    implementation(projects.feature.user)

    implementation(libs.exposedCore)
    implementation(libs.exposedJdbc)
    implementation(libs.postgresJdbc)

    implementation(libs.mdi)
    implementation(projects.libs.utf8Checker.usecaseIntegration)
    implementation(projects.migrations)
    implementation(projects.endpointsNew)
    implementation(projects.libs.discordWebhook.ktor)

    testImplementation(libs.meetacySdk.apiKtor)
    testImplementation(libs.coroutinesTest)
    testImplementation(libs.ktorClient.core)
    testImplementation(libs.ktorClient.cio)
    testImplementation(libs.ktorClient.logging)
    testImplementation(libs.ktorClient.mock)
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
    mainClass = "app.meetacy.backend.MainKt"
}

tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions.freeCompilerArgs += "-opt-in=kotlinx.serialization.ExperimentalSerializationApi"
}
