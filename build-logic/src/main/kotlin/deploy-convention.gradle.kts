import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import deploy.DeployExtension
import deploy.SshSessionExtension
import org.hidetake.groovy.ssh.connection.AllowAnyHosts
import org.hidetake.groovy.ssh.core.Remote
import org.jetbrains.kotlin.konan.properties.loadProperties
import java.io.File
import java.util.*

plugins {
    application
    id("com.github.johnrengelman.shadow")
    id("org.hidetake.ssh")
}

val extension = project.extensions.create<DeployExtension>(name = "deploy")

project.afterEvaluate {
    val default = extension.targets["default"]
    extension.targets.filter { it.key != "default" }.forEach { configuration ->
        configuration.value.apply {
            host ?: default?.host ?: error("`host` should be defined in `deploy`")
            destination ?: default?.destination ?: error("`destination` should be defined in `deploy`")
            mainClass ?: default?.mainClass ?: error("`mainClass` should be defined in `deploy`")
            serviceName ?: default?.serviceName ?: error("`service name` should be defined in `deploy`")
        }

        val shadowJar = tasks.named<ShadowJar>("shadowJar") {
            archiveFileName.set(configuration.value.archiveName ?: default?.archiveName)
            mergeServiceFiles()
            manifest {
                attributes(mapOf("Main-Class" to (configuration.value.mainClass ?: default?.mainClass)))
            }
        }

        val webServer = Remote(
            mapOf(
                "host" to (configuration.value.host ?: default?.host),
                "user" to (configuration.value.user ?: default?.user),
                "password" to (configuration.value.password ?: default?.password),
                "knownHosts" to ((configuration.value.knownHostsFile ?: default?.knownHostsFile)?.let(::File)
                    ?: AllowAnyHosts.instance)
            )
        )

        project.extensions.create<SshSessionExtension>("${configuration.key}SshSession", project, webServer)

        project.task("${configuration.key}Deploy") {
            group = "deploy"
            dependsOn(shadowJar)

            doLast {
                project.extensions.getByName<SshSessionExtension>("${configuration.key}SshSession").invoke {
                    put(
                        hashMapOf(
                            "from" to shadowJar.get().archiveFile.get().asFile,
                            "into" to configuration.value.destination
                        )
                    )
                    execute("systemctl restart ${configuration.value.serviceName ?: default?.serviceName}")
                }
            }
        }
    }
}

fun Project.properties(): Properties? =
    rootProject.file("deploy.properties")
        .takeIf(File::exists)
        ?.run { loadProperties(absolutePath) }
