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
    implementation(projects.application.database)
    implementation(projects.application.endpoints)
    implementation(projects.application.usecase)
    implementation(projects.core.types.integration)
    api(libs.ktor.server.core)
    api(libs.ktor.server.cio)

    implementation(libs.exposed.core)
    implementation(libs.exposed.jdbc)
    implementation(libs.postgres.jdbc)

    implementation(libs.meetacy.di.core)
    implementation(projects.libs.discordWebhook.ktor)

    testImplementation(libs.meetacy.sdk.api.ktor)

    testImplementation(libs.h2.jdbc)

    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.kotlinx.serialization.json)

    testImplementation(libs.ktor.client.core)
    testImplementation(libs.ktor.client.cio)
    testImplementation(libs.ktor.client.logging)
    testImplementation(libs.ktor.client.mock)

    testImplementation(kotlin("test"))
    testImplementation(projects.libs.paging.serializable)
    testImplementation(projects.libs.ktorExtensions)
}

tasks.test {
    useJUnitPlatform()
    testLogging {
        exceptionFormat = TestExceptionFormat.FULL
    }
//    systemProperty("IS_TEST", true)
    setEnvironment("IS_TEST" to true)
    setEnvironment("DATABASE_URL" to "jdbc:h2:mem:test")
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

tasks.withType<KotlinCompile> {
    kotlinOptions.languageVersion = "2.0"
}
