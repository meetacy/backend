@file:Suppress("NAME_SHADOWING")

package app.meetacy.backend

import app.meetacy.backend.application.database.DatabaseConfig
import app.meetacy.backend.application.endpoints.prepareEndpoints
import app.meetacy.backend.database.initDatabase
import app.meetacy.backend.di.di
import app.meetacy.backend.run.runProductionServer
import app.meetacy.backend.types.files.FileSize
import app.meetacy.di.builder.di
import io.ktor.server.engine.*
import org.jetbrains.exposed.sql.Database
import java.io.File

suspend fun main() {
    val webhookUrl = System.getenv("DISCORD_WEBHOOK_URL")
    runProductionServer(webhookUrl) {
        val server = runServer()
        initialized = true
        server.start(wait = true)
    }
}

suspend fun runServer(
    port: Int = System.getenv("PORT")?.toIntOrNull() ?: 8080,
    databaseUrl: String = System.getenv("DATABASE_URL") ?: error("Please provide a database url"),
    databaseUser: String = System.getenv("DATABASE_USER") ?: "",
    databasePassword: String = System.getenv("DATABASE_PASSWORD") ?: "",
    filesBasePath: String = System.getenv("FILES_BASE_PATH") ?: File(
        /* parent = */ System.getenv("user.dir"),
        /* child = */ "files"
    ).apply { mkdirs() }.absolutePath,
    filesSizeLimit: Long = System.getenv("FILES_SIZE_LIMIT")?.toLongOrNull() ?: (99L * 1024 * 1024),
    isTest: Boolean = System.getenv("IS_TEST").toBoolean(),
): ApplicationEngine {
    val di = di {
        val port by constant(port)
        val databaseConfig by constant(
            DatabaseConfig(databaseUrl, databaseUser, databasePassword, isTest)
        )
        val filesBasePath by constant(filesBasePath)
        val filesSizeLimit by constant(FileSize(filesSizeLimit))
        val deleteFilesOnExit by constant(value = false)
    } + di()

    val database: Database by di.getting
    initDatabase(database)

    return prepareEndpoints(di)
}
