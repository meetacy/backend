@file:Suppress("NAME_SHADOWING")

package app.meetacy.backend

import app.meetacy.backend.application.database.DatabaseConfig
import app.meetacy.backend.database.initDatabase
import app.meetacy.backend.application.endpoints.prepareEndpoints
import app.meetacy.backend.di.di
import app.meetacy.backend.run.runProductionServer
import app.meetacy.backend.types.files.FileSize
import app.meetacy.di.builder.di
import app.meetacy.di.global.GlobalDI
import org.jetbrains.exposed.sql.Database
import java.io.File

suspend fun main() {
    val port = System.getenv("PORT")?.toIntOrNull() ?: 8080
    val databaseUrl = System.getenv("DATABASE_URL") ?: error("Please provide a database url")
    val databaseUser = System.getenv("DATABASE_USER") ?: ""
    val databasePassword = System.getenv("DATABASE_PASSWORD") ?: ""
    val filesBasePath = System.getenv("FILES_BASE_PATH") ?: File(
        /* parent = */ System.getenv("user.dir"),
        /* child = */ "files"
    ).apply { mkdirs() }.absolutePath
    val filesSizeLimit = System.getenv("FILES_SIZE_LIMIT")?.toLongOrNull() ?: (100L * 1024 * 1024)
    val webhookUrl = System.getenv("DISCORD_WEBHOOK_URL")

    runProductionServer(webhookUrl) {
        val di = di() + di {
            val port by constant(port)
            val databaseConfig by constant(
                DatabaseConfig(databaseUrl, databaseUser, databasePassword)
            )
            val filesBasePath by constant(filesBasePath)
            val filesSizeLimit by constant(FileSize(filesSizeLimit))
            val deleteFilesOnExit by constant(value = true)
        }

        GlobalDI.init(di)

        val database: Database by di.getting
        initDatabase(database)

        val server = prepareEndpoints()
        initialized = true
        server.start(wait = true)
    }
}
